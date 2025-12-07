package com.islamicapp.siraathalmusthaqeem.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.home.BottomNavItem
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HomeBottomBar(navController: NavHostController) {
    // Get the current route from the navigation controller
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = colorResource(id = R.color.green),
        contentColor = colorResource(id = R.color.white)
    ) {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Quran,
            BottomNavItem.Dua,
            BottomNavItem.Tasbeeh
        ).forEach { item ->
            // Check if this item's route matches the current route
            val isSelected = currentRoute == item.route

            val iconColor = if (isSelected) {
                colorResource(id = R.color.white) // Bright white when selected
            } else {
                colorResource(id = R.color.white).copy(alpha = 0.5f) // More dimmed when unselected
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(BottomNavItem.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(if (isSelected) 26.dp else 24.dp), // Slightly larger when selected
                        colorFilter = ColorFilter.tint(iconColor)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, // Bold when selected
                        fontSize = if (isSelected) 13.sp else 12.sp // Slightly larger text when selected
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colorResource(id = R.color.white).copy(alpha = 0.2f),
                    selectedIconColor = colorResource(id = R.color.white),
                    unselectedIconColor = colorResource(id = R.color.white).copy(alpha = 0.5f),
                    selectedTextColor = colorResource(id = R.color.white),
                    unselectedTextColor = colorResource(id = R.color.white).copy(alpha = 0.5f)
                )
            )
        }
    }
}
