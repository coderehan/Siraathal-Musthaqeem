package com.islamicapp.siraathalmusthaqeem.screen.home

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.FlowRow
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
import com.islamicapp.siraathalmusthaqeem.screen.home.components.DailySlider
import kotlinx.coroutines.launch
import com.islamicapp.siraathalmusthaqeem.screen.navigation.NavigationItem
import android.provider.Settings

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

    // Selected bottom nav item (simple state for preview/demo)
    var selectedBottom by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }

    val features = listOf(
        Feature("Prayer Times", R.drawable.ic_android, NavigationItem.PrayerTimes.route),
        Feature("Qibla", R.drawable.ic_android, NavigationItem.Qibla.route),
        Feature("Masjid Near Me", R.drawable.ic_android, NavigationItem.MasjidNearMe.route),
        Feature("Allah 99 Names", R.drawable.ic_android, NavigationItem.Allah99Names.route),
        Feature("Zakath", R.drawable.ic_android, NavigationItem.Zakath.route),
        Feature("Pilgrimage", R.drawable.ic_android, NavigationItem.Pilgrimage.route),
        Feature("Hadith", R.drawable.ic_android, NavigationItem.Hadith.route),
        Feature("Seerah", R.drawable.ic_android, NavigationItem.Seerah.route),
        Feature("Prophets", R.drawable.ic_android, NavigationItem.Prophets.route),
    )

    @Suppress("DEPRECATION")
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||
                locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Navigation Drawer
            ModalDrawerSheet {
                // Drawer header image
                Image(
                    painter = painterResource(id = R.drawable.splash),
                    contentDescription = "Drawer Header",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Drawer menu items
//                DrawerMenuItem("Theme", R.drawable.ic_android) {
//                    scope.launch { drawerState.close() }
//                    navController.navigate("theme") {
//                        launchSingleTop = true
//                    }
//                }
                DrawerMenuItem("Edit Profile", R.drawable.ic_android) {
                    scope.launch { drawerState.close() }
                    navController.navigate(NavigationItem.Profile.route)
                }
                // Request a feature
                DrawerMenuItem("Request a feature", R.drawable.ic_android) {
                    scope.launch { drawerState.close() }
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("mohammedrehanm1137@gmail.com"))
                        putExtra(
                            Intent.EXTRA_SUBJECT,
                            "Feature Request - Siraathal Musthaqeem App"
                        )
                    }
                    // Use runCatching to avoid crashes if no email app is found
                    runCatching {
                        context.startActivity(Intent.createChooser(intent, "Send email"))
                    }
                }

                // Report a bug
                DrawerMenuItem("Report a bug/issue", R.drawable.ic_android) {
                    scope.launch { drawerState.close() }
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("mohammedrehanm1137@gmail.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "Bug Report - Siraathal Musthaqeem App")
                    }
                    // Use runCatching to avoid crashes if no email app is found
                    runCatching {
                        context.startActivity(Intent.createChooser(intent, "Send email"))
                    }
                }

                // Rate the app
                DrawerMenuItem("Rate the App", R.drawable.ic_android) {
                    scope.launch {
                        drawerState.close()
                        val appPackageName = context.packageName
                        val playStoreIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$appPackageName")
                        ).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }

                        runCatching {
                            context.startActivity(playStoreIntent)
                        }.onFailure {
                            // fallback to browser
                            context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                                )
                            )
                        }
                    }
                }

                // Share the app
                DrawerMenuItem("Share the App", R.drawable.ic_android) {
                    scope.launch {
                        drawerState.close()
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "✨ Discover *Siraathal Musthaqeem* — your complete Islamic companion app.\n" +
                                        "Read Quran, explore powerful Duas, track Salah times and much more.\n\n" +
                                        "Download now and stay connected to Allah:\n" +
                                        "https://play.google.com/store/apps/details?id=${context.packageName}"
                            )
                        }
                        runCatching {
                            context.startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    "Share App via"
                                )
                            )
                        }
                    }
                }

                DrawerMenuItem("About the App", R.drawable.ic_android) {
                    scope.launch {
                        drawerState.close()
                        navController.navigate("about")
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "v 1.0",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Assalamu Alaikum, ${if (userName.isNotEmpty()) userName else "Believer"}!",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
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
            bottomBar = {
                NavigationBar {
                    listOf(
                        BottomNavItem.Home,
                        BottomNavItem.Quran,
                        BottomNavItem.Dua,
                        BottomNavItem.Tasbeeh
                    ).forEach { item ->
                        NavigationBarItem(
                            selected = selectedBottom == item,
                            onClick = {
                                selectedBottom = item
                                navController.navigate(item.route) {
                                    popUpTo(BottomNavItem.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) }
                        )
                    }
                }
            },
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
                            Icon(
                                painter = painterResource(id = R.drawable.ic_azaan_speaker),
                                contentDescription = "Play Azaan",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Responsive Features Grid
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp) // REMOVE any extra padding here if needed, but it seems fine.
                    ) {
                        val itemSpacing = 16.dp
                        val columns = 3

                        // FIX: Use a slightly safer calculation to ensure 3 items fit on all screens.
                        // We deduct the total required space for gaps (itemSpacing * 2)
                        // and divide the remainder by 3.
                        val itemWidth = (maxWidth - (itemSpacing * (columns - 1))) / columns

                        FlowRow(
                            // Enforcing 3 items per row to solve the wrapping issue
                            maxItemsInEachRow = columns,
                            horizontalArrangement = Arrangement.spacedBy(itemSpacing),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            features.forEach { feature ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    // Apply the calculated width
                                    modifier = Modifier
                                        .width(itemWidth)
                                        .clickable {
                                            when (feature.route) {
                                                NavigationItem.MasjidNearMe.route -> {
                                                    if (isLocationEnabled(context)) {
                                                        val gmmIntentUri =
                                                            Uri.parse("geo:0,0?q=masjid near me")
                                                        val mapIntent = Intent(
                                                            Intent.ACTION_VIEW,
                                                            gmmIntentUri
                                                        ).apply {
                                                            setPackage("com.google.android.apps.maps")
                                                        }
                                                        runCatching {
                                                            context.startActivity(
                                                                mapIntent
                                                            )
                                                        }.onFailure {
                                                            // Fallback to browser if Maps app is not installed
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
                                                            "Location is off. Please enable it to view nearby masjids.",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                                                    }
                                                }
                                                // Default case: Navigate for all other features with defined internal routes
                                                else -> navController.navigate(feature.route)
                                            }
                                        }
                                ) {
                                    Surface(
                                        tonalElevation = 1.dp,
                                        shape = MaterialTheme.shapes.medium,
                                        modifier = Modifier
                                            .size(itemWidth * 0.7f)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Image(
                                                painter = painterResource(id = feature.imageRes),
                                                contentDescription = feature.title,
                                                modifier = Modifier.size(itemWidth * 0.4f)
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

                    Spacer(modifier = Modifier.height(24.dp))

                    DailySlider()
                }
            }
        )
    }
}

@Composable
private fun DrawerMenuItem(
    label: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(text = label) },
        selected = false,
        onClick = onClick,
        icon = { Icon(painter = painterResource(id = iconRes), contentDescription = label) },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

data class Feature(
    val title: String,
    val imageRes: Int,
    val route: String
)

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    object Home : BottomNavItem("Home", R.drawable.ic_android, "home")
    object Quran : BottomNavItem("Quran", R.drawable.ic_android, "quran")
    object Dua : BottomNavItem("Dua", R.drawable.ic_android, "dua")
    object Tasbeeh : BottomNavItem("Tasbeeh", R.drawable.ic_android, "tasbeeh")
}