package com.islamicapp.siraathalmusthaqeem.screen.tasbeeh.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CustomTargetDialog(
    currentTarget: Int,
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit
) {
    var textValue by remember { mutableStateOf(currentTarget.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set Custom Target") },
        text = {
            Column {
                OutlinedTextField(
                    value = textValue,
                    onValueChange = { textValue = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Enter target count") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val target = textValue.toIntOrNull() ?: currentTarget
                onSave(target)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}