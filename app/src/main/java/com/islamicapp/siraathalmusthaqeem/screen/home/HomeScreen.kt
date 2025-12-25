package com.islamicapp.siraathalmusthaqeem.screen.home

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import com.islamicapp.siraathalmusthaqeem.screen.hijricalendar.HijriCalendarScreen
import com.islamicapp.siraathalmusthaqeem.screen.home.components.dailyslider.DailySlider
import kotlinx.coroutines.launch
import com.islamicapp.siraathalmusthaqeem.screen.navigation.NavigationItem
import android.provider.Settings
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.islamicapp.siraathalmusthaqeem.screen.quran.pdfmetadata.QuranPdfManager
import kotlinx.coroutines.Dispatchers
import com.islamicapp.siraathalmusthaqeem.screen.home.components.HomeDrawerContent
import com.islamicapp.siraathalmusthaqeem.screen.home.components.HomeBottomBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    preferencesManager: PreferencesManager
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Collect name from DataStore
    val userName by preferencesManager.userNameFlow.collectAsState(initial = "")

    val pdfManager = remember { QuranPdfManager(context) }

    // Silently copy PDF from assets to internal storage on first launch
    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            try {
                if (!pdfManager.isPdfReady()) {
                    pdfManager.copyPdfFromAssets()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val features = listOf(
        Feature("Prayer Times", R.drawable.home_icon_1, NavigationItem.PrayerTimes.route),
        Feature("Qibla", R.drawable.home_icon_2, NavigationItem.Qibla.route),
        Feature("Masjid Near Me", R.drawable.home_icon_3, NavigationItem.MasjidNearMe.route),
        Feature("Names of Allah", R.drawable.home_icon_4, NavigationItem.Allah99Names.route),
        Feature("Zakath", R.drawable.home_icon_5, NavigationItem.Zakath.route),
        Feature("Pilgrimage", R.drawable.home_icon_6, NavigationItem.Pilgrimage.route),
        Feature("Hadith", R.drawable.home_icon_7, NavigationItem.Hadith.route),
        Feature("Seerah", R.drawable.home_icon_8, NavigationItem.Seerah.route),
        Feature("Prophets", R.drawable.home_icon_9, NavigationItem.Prophets.route),
    )

    @Suppress("DEPRECATION")
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||
                locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        // Navigation Drawer
        drawerContent = { HomeDrawerContent(navController, drawerState) }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Assalamu Alaikum, ${if (userName.isNotEmpty()) userName else ""}!",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.green),
                        titleContentColor = colorResource(id = R.color.white),
                        navigationIconContentColor = colorResource(id = R.color.white)
                    ),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Open drawer"
                            )
                        }
                    }
                )
            },
            // Bottom Navigation
            bottomBar = { HomeBottomBar(navController = navController) },

            // Main Content
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(12.dp))


                    // Date Row with Hijri Calendar and Speaker Icon
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Hijri Calendar Component
                        Box(modifier = Modifier.weight(1f)) {
                            HijriCalendarScreen()
                        }

                        // Azaan Icon
                        IconButton(
                            onClick = {
                                navController.navigate("azaan")
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.adhan),
                                contentDescription = "Play Azaan",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Responsive Features Grid 3*3
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val columns = 3
                        val spacing = 16.dp
                        val itemWidth = (maxWidth - spacing * (columns - 1)) / columns

                        Column(
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            features.chunked(columns).forEach { rowFeatures ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(spacing),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    rowFeatures.forEach { feature ->
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier
                                                .width(itemWidth)
                                                .clickable {
                                                    when (feature.route) {
                                                        NavigationItem.MasjidNearMe.route -> {
                                                            if (isLocationEnabled(context)) {
                                                                val gmmIntentUri = Uri.parse("geo:0,0?q=masjid near me")
                                                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                                                                    setPackage("com.google.android.apps.maps")
                                                                }
                                                                runCatching { context.startActivity(mapIntent) }
                                                                    .onFailure {
                                                                        context.startActivity(
                                                                            Intent(
                                                                                Intent.ACTION_VIEW,
                                                                                Uri.parse("https://www.google.com/maps/search/masjid+near+me")
                                                                            )
                                                                        )
                                                                    }
                                                            } else {
                                                                Toast.makeText(
                                                                    context,
                                                                    "Location is off. Please enable it.",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                                                            }
                                                        }
                                                        else -> navController.navigate(feature.route)
                                                    }
                                                }
                                        ) {
                                            // This is card size height and width i.e. outer part
                                            Surface(
                                                tonalElevation = 1.dp,
                                                shape = MaterialTheme.shapes.medium,
                                                modifier = Modifier
                                                    .size(itemWidth * 0.8f)
                                                    .border(
                                                        width = 2.dp,
                                                        color = colorResource(id = R.color.green),
                                                        shape = MaterialTheme.shapes.medium
                                                    ),
                                                color = Color.White
                                            ) {
                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    // This is inner part i.e. image size
                                                    Image(
                                                        painter = painterResource(id = feature.imageRes),
                                                        contentDescription = feature.title,
                                                        modifier = Modifier.size(itemWidth * 0.9f)
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Text(
                                                text = feature.title,
                                                style = MaterialTheme.typography.bodyMedium,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    DailySlider()
                }
            }
        )
    }
}

@Composable
fun DrawerMenuItem(
    label: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(text = label) },
        selected = false,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(28.dp)
            )
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

data class Feature(
    val title: String,
    val imageRes: Int,
    val route: String
)

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    object Home : BottomNavItem("Home", R.drawable.home, "home")
    object Quran : BottomNavItem("Quran", R.drawable.quran, "quran")
    object Dua : BottomNavItem("Dua", R.drawable.dua, "dua")
    object Tasbeeh : BottomNavItem("Tasbeeh", R.drawable.tasbeeh, "tasbeeh")
}