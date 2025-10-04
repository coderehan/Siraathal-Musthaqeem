package com.islamicapp.siraathalmusthaqeem.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.islamicapp.siraathalmusthaqeem.screen.navigation.NavigationItem
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavHostController, preferencesManager: PreferencesManager) {
    var name by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) } // Track error state
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Enter your name here",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Name input with error support
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (isError && name.isNotEmpty()) {
                        isError = false // reset error when user types something
                    }
                },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                singleLine = true
            )

            // Show error message below text field
            if (isError) {
                Text(
                    text = "Please enter your name",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 4.dp, start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.isNotEmpty()) {
                        scope.launch {
                            // Save both name & profile status
                            preferencesManager.saveUserName(name)
                            preferencesManager.setProfileCompleted(true)

                            navController.navigate(NavigationItem.Home.route) {
                                popUpTo(NavigationItem.Profile.route) { inclusive = true }
                            }
                        }
                    } else {
                        // Show error when empty
                        isError = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("NEXT")
            }
        }
    }
}
