package com.islamicapp.siraathalmusthaqeem.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.islamicapp.siraathalmusthaqeem.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                DrawerMenuItem("Edit Profile", R.drawable.ic_android) { scope.launch { drawerState.close() } }
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
                    title = { Text(text = "Assalamu Alaikum, Rehan!", style = MaterialTheme.typography.titleMedium) },
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
                                    popUpTo(BottomNavItem.Home.route) { saveState = true } // ✅ fixed here
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
                        .verticalScroll(rememberScrollState()) // whole screen scrolls
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(12.dp))

                    // Date Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "4 Dhul-Hijjah 1446 AH",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                            )
                            Text(
                                text = "1 June 2025, Sunday",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(
                            onClick = { /* TODO: play daily reminder */ },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_android),
                                contentDescription = "Speaker"
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
                                        .width(itemWidth) // auto-calculated width
                                        .clickable { navController.navigate(feature.route) }
                                ) {
                                    Surface(
                                        tonalElevation = 1.dp,
                                        shape = MaterialTheme.shapes.medium,
                                        modifier = Modifier
                                            .size(itemWidth * 0.7f) // proportional size
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
fun DailySlider() {
    val items = listOf("Daily Hadith", "Daily Aayah", "Daily Dua")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { items.size })
    val scope = rememberCoroutineScope()

    // Auto-scroll every 3 seconds
    LaunchedEffect(pagerState.currentPage) {
        delay(3000)
        val nextPage = (pagerState.currentPage + 1) % items.size
        scope.launch { pagerState.animateScrollToPage(nextPage) }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = items[page], style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dots indicator
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(items.size) { index ->
                val selected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (selected) 12.dp else 10.dp)
                        .background(
                            if (selected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            shape = MaterialTheme.shapes.small
                        )
                )
            }
        }
    }
}

@Composable
private fun DrawerMenuItem(label: String, iconRes: Int, onClick: () -> Unit) {
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
    val route: String // navigation route
)


sealed class BottomNavItem(val title: String, val icon: Int,  val route: String) {
    object Home : BottomNavItem("Home", R.drawable.ic_android, "home")
    object Quran : BottomNavItem("Quran", R.drawable.ic_android, "quran")
    object Dua : BottomNavItem("Dua", R.drawable.ic_android, "dua")
    object Tasbeeh : BottomNavItem("Tasbeeh", R.drawable.ic_android, "tasbeeh")
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
