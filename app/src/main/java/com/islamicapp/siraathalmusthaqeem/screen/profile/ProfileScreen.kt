package com.islamicapp.siraathalmusthaqeem.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.navigation.NavigationItem
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavHostController, preferencesManager: PreferencesManager) {
    var name by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // 1. Outer Box with Green Background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.green)),
        contentAlignment = Alignment.Center
    ) {
        // 2. White Card in the Center
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp), // External padding so card doesn't touch screen edges
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            // 3. Content inside the Card
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp) // Internal padding inside the card
            ) {
                Text(
                    text = "Enter your name here",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.Black // Ensure text is visible on white background
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Name input with filtering and length limit
                OutlinedTextField(
                    value = name,
                    onValueChange = { newValue ->
                        // 1. Only allow letters AND SPACES
                        // 2. Enforce a 15-character limit
                        if (newValue.length <= 15 && newValue.matches(Regex("^[a-zA-Z ]*$"))) {
                            name = newValue
                        }
                    },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.green),
                        unfocusedBorderColor = colorResource(id = R.color.green),
                        focusedLabelColor = colorResource(id = R.color.green),
                        cursorColor = colorResource(id = R.color.green),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        val trimmedName = name.trim()
                        scope.launch {
                            // Save both name & profile status
                            preferencesManager.saveUserName(trimmedName)
                            preferencesManager.setProfileCompleted(true)

                            navController.navigate(NavigationItem.Home.route) {
                                popUpTo(NavigationItem.Profile.route) { inclusive = true }
                            }
                        }
                    },
                    // Button is enabled only when the name is not empty (after trimming spaces)
                    enabled = name.trim().isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.green),
                        contentColor = Color.White,
                        disabledContainerColor = colorResource(id = R.color.green).copy(alpha = 0.5f)
                    )
                ) {
                    Text("NEXT")
                }
            }
        }
    }
}