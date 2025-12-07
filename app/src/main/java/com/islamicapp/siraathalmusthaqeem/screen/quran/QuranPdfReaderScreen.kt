package com.islamicapp.siraathalmusthaqeem.screen.quran

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.screen.quran.pdfmetadata.QuranMetadataProvider
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.quran.pdfmetadata.QuranPdfManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranPdfReaderScreen(
    navController: NavController,
    surahNumber: Int? = null,
    juzNumber: Int? = null
) {
    val context = LocalContext.current
    val pdfManager = remember { QuranPdfManager(context) }
    val scope = rememberCoroutineScope()

    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var pdfRenderer by remember { mutableStateOf<PdfRenderer?>(null) }
    var totalPages by remember { mutableStateOf(0) }
    var initialPage by remember { mutableStateOf(0) }

    val title = remember(surahNumber, juzNumber) {
        when {
            surahNumber != null -> {
                QuranMetadataProvider.getSurahMetadata(surahNumber)?.name ?: "Surah $surahNumber"
            }
            juzNumber != null -> "Juz $juzNumber"
            else -> "Quran"
        }
    }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            try {
                Log.d("QuranPDF", "Starting PDF initialization...")

                if (!pdfManager.isPdfReady()) {
                    Log.d("QuranPDF", "Copying PDF from assets...")
                    pdfManager.copyPdfFromAssets()
                } else {
                    Log.d("QuranPDF", "PDF already exists")
                }

                val pdfPath = pdfManager.getLocalPdfPath()
                if (pdfPath == null) {
                    withContext(Dispatchers.Main) {
                        errorMessage = "Failed to load Quran PDF"
                        isLoading = false
                    }
                    return@launch
                }

                val file = File(pdfPath)
                Log.d("QuranPDF", "File size: ${file.length()}")

                val fileDescriptor = ParcelFileDescriptor.open(
                    file,
                    ParcelFileDescriptor.MODE_READ_ONLY
                )
                val renderer = PdfRenderer(fileDescriptor)
                totalPages = renderer.pageCount
                Log.d("QuranPDF", "Total pages in PDF: $totalPages")

                // NO OFFSET - Use metadata page numbers directly (already correct)
                val startPage = when {
                    surahNumber != null -> {
                        val metadata = QuranMetadataProvider.getSurahMetadata(surahNumber)
                        val page = (metadata?.startPage ?: 1) - 1 // Convert to 0-indexed
                        Log.d(
                            "QuranPDF",
                            "Surah $surahNumber -> Page ${metadata?.startPage} (0-indexed: $page)"
                        )
                        page
                    }

                    juzNumber != null -> {
                        val metadata = QuranMetadataProvider.getJuzMetadata(juzNumber)
                        val page = (metadata?.startPage ?: 1) - 1
                        Log.d(
                            "QuranPDF",
                            "Juz $juzNumber -> Page ${metadata?.startPage} (0-indexed: $page)"
                        )
                        page
                    }

                    else -> 0
                }.coerceIn(0, totalPages - 1)

                Log.d("QuranPDF", "Final start page: $startPage")

                withContext(Dispatchers.Main) {
                    pdfRenderer = renderer
                    initialPage = startPage
                    isLoading = false
                    Log.d("QuranPDF", "UI updated, will show page: $startPage")
                }

            } catch (e: Exception) {
                Log.e("QuranPDF", "Error: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    errorMessage = "Error: ${e.message}"
                    isLoading = false
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                pdfRenderer?.close()
            } catch (e: Exception) {
                Log.e("QuranPDF", "Error closing: ${e.message}")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.green),
                    titleContentColor = colorResource(id = R.color.white),
                    navigationIconContentColor = colorResource(id = R.color.white)
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = colorResource(id = R.color.green))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading Quran...")
                    }
                }

                errorMessage != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(errorMessage ?: "Error", color = Color.Red)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { navController.popBackStack() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.green)
                            )
                        ) {
                            Text("Go Back")
                        }
                    }
                }

                pdfRenderer != null && totalPages > 0 -> {
                    val pagerState = rememberPagerState(
                        initialPage = initialPage,
                        pageCount = { totalPages }
                    )

                    // Ensure this HorizontalPager block is correctly implemented:
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        reverseLayout = true
                    ) { pageIndex ->
                        PdfPageRenderer(
                            pdfRenderer = pdfRenderer!!,
                            pageIndex = pageIndex
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PdfPageRenderer(
    pdfRenderer: PdfRenderer,
    pageIndex: Int
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isRendering by remember { mutableStateOf(false) }
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(pageIndex) {
        // Reset zoom/pan when page changes
        scale = 1f
        offsetX = 0f
        offsetY = 0f

        if (bitmap == null && !isRendering) {
            isRendering = true
            scope.launch(Dispatchers.IO) {
                try {
                    var pageBitmap: Bitmap? = null
                    synchronized(pdfRenderer) {
                        val page = pdfRenderer.openPage(pageIndex)
                        val originalWidth = page.width
                        val originalHeight = page.height

                        pageBitmap = Bitmap.createBitmap(
                            originalWidth * 2,
                            originalHeight * 2,
                            Bitmap.Config.ARGB_8888
                        )
                        page.render(pageBitmap!!, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                        page.close()
                    }
                    withContext(Dispatchers.Main) {
                        bitmap = pageBitmap
                        isRendering = false
                    }
                } catch (e: Exception) {
                    Log.e("QuranPDF", "Render error page $pageIndex: ${e.message}")
                    withContext(Dispatchers.Main) {
                        isRendering = false
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        bitmap?.let { bmp ->
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = "Page ${pageIndex + 1}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.707f)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offsetX,
                        translationY = offsetY
                    )
                    // ZOOM gesture - always available for pinch zoom
                    .pointerInput(pageIndex) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            if (zoom != 1f) {
                                val newScale = (scale * zoom).coerceIn(1f, 4f)
                                val maxOffsetX = (size.width * (newScale - 1) / 2f)
                                val maxOffsetY = (size.height * (newScale - 1) / 2f)

                                offsetX = (offsetX + pan.x).coerceIn(-maxOffsetX, maxOffsetX)
                                offsetY = (offsetY + pan.y).coerceIn(-maxOffsetY, maxOffsetY)
                                scale = newScale
                            }
                        }
                    }
                    // DOUBLE TAP to zoom
                    .pointerInput(pageIndex) {
                        detectTapGestures(
                            onDoubleTap = {
                                if (scale > 1f) {
                                    scale = 1f
                                    offsetX = 0f
                                    offsetY = 0f
                                } else {
                                    scale = 2f
                                }
                            }
                        )
                    }
                    // DRAG gesture - ONLY add when zoomed in
                    .then(
                        if (scale > 1f) {
                            Modifier.pointerInput(pageIndex, scale) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()

                                    val maxOffsetX = (size.width * (scale - 1) / 2f)
                                    val maxOffsetY = (size.height * (scale - 1) / 2f)

                                    offsetX = (offsetX + dragAmount.x).coerceIn(-maxOffsetX, maxOffsetX)
                                    offsetY = (offsetY + dragAmount.y).coerceIn(-maxOffsetY, maxOffsetY)
                                }
                            }
                        } else {
                            Modifier // Empty modifier when not zoomed - allows HorizontalPager to work
                        }
                    ),
                contentScale = ContentScale.Fit
            )
        } ?: if (isRendering) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = colorResource(id = R.color.green)
            )
        } else {

        }
    }
}