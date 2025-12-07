package com.islamicapp.siraathalmusthaqeem.screen.home.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.home.DrawerMenuItem
import com.islamicapp.siraathalmusthaqeem.screen.navigation.NavigationItem
import kotlinx.coroutines.launch

@Composable
fun HomeDrawerContent(
    navController: NavHostController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxWidth(0.8f) // Take up 80% of screen width
            .then(Modifier.widthIn(max = 360.dp)) // But cap it at 360dp on large screens
    ) {
        // Drawer header image
        Image(
            painter = painterResource(id = R.drawable.nav_header),
            contentDescription = "Drawer Header",
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Drawer menu items
        DrawerMenuItem("Edit Profile", R.drawable.nav_drawer_1) {
            scope.launch { drawerState.close() }
            navController.navigate(NavigationItem.Profile.route)
        }
        // Request a feature
        DrawerMenuItem("Request a feature", R.drawable.nav_drawer_2) {
            scope.launch { drawerState.close() }
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("mohammedrehanm1137@gmail.com"))
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Feature Request - Siraathal Musthaqeem App"
                )
            }
            runCatching { context.startActivity(Intent.createChooser(intent, "Send email")) }
        }

        // Report a bug
        DrawerMenuItem("Report a bug/issue", R.drawable.nav_drawer_3) {
            scope.launch { drawerState.close() }
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("mohammedrehanm1137@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Bug Report - Siraathal Musthaqeem App")
            }
            runCatching { context.startActivity(Intent.createChooser(intent, "Send email")) }
        }

        // Rate the app
        DrawerMenuItem("Rate the App", R.drawable.nav_drawer_4) {
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
        DrawerMenuItem("Share the App", R.drawable.nav_drawer_5) {
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

        DrawerMenuItem("About the App", R.drawable.nav_drawer_6) {
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