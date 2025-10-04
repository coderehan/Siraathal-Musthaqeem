package com.islamicapp.siraathalmusthaqeem.screen.home

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    preferencesManager: PreferencesManager
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Collect name from DataStore
    val userName by preferencesManager.userNameFlow.collectAsState(initial = "")

    // Selected bottom nav item (simple state for preview/demo)
    var selectedBottom by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }

    val features = listOf(
        Feature("Prayer Times", R.drawable.ic_android, "prayer_times"),
        Feature("Qibla", R.drawable.ic_android, "qibla"),
        Feature("Masjid", R.drawable.ic_android, "masjid"),
        Feature("Allah 99 Names", R.drawable.ic_android, "allah_99_names"),
        Feature("Zakath", R.drawable.ic_android, "zakath"),
        Feature("Pilgrimage", R.drawable.ic_android, "pilgrimage"),
        Feature("Hadith", R.drawable.ic_android, "hadith"),
        Feature("Seerah", R.drawable.ic_android, "seerah"),
        Feature("Prophets", R.drawable.ic_android, "prophets"),
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Navigation Drawer
            ModalDrawerSheet {
                // Drawer header image
                Image(
                    painter = painterResource(id = R.drawable.ic_android),
                    contentDescription = "Drawer Header",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Drawer menu items
                DrawerMenuItem("Theme", R.drawable.ic_android) { scope.launch { drawerState.close() } }
                DrawerMenuItem("Edit Profile", R.drawable.ic_android) {
                    scope.launch { drawerState.close() }
                    navController.navigate(NavigationItem.Profile.route)
                }
                DrawerMenuItem("Request a feature", R.drawable.ic_android) { scope.launch { drawerState.close() } }
                DrawerMenuItem("Report a bug/issue", R.drawable.ic_android) { scope.launch { drawerState.close() } }
                DrawerMenuItem("Rate the App", R.drawable.ic_android) { scope.launch { drawerState.close() } }
                DrawerMenuItem("Share the App", R.drawable.ic_android) { scope.launch { drawerState.close() } }
                DrawerMenuItem("About the App", R.drawable.ic_android) { scope.launch { drawerState.close() } }

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
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Open drawer")
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

                        // Speaker Icon
                        IconButton(
                            onClick = { /* TODO: play daily reminder */ },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_android),
                                contentDescription = "Play daily reminder",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Responsive Features Grid
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val itemSpacing = 16.dp
                        val columns = 3
                        val itemWidth = (maxWidth - (itemSpacing * (columns - 1))) / columns

                        FlowRow(
                            maxItemsInEachRow = columns,
                            horizontalArrangement = Arrangement.spacedBy(itemSpacing),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            features.forEach { feature ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .width(itemWidth)
                                        .clickable { navController.navigate(feature.route) }
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