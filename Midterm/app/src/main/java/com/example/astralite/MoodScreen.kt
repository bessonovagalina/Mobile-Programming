
package com.example.astralite

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MoodScreen(
    onSaveMood: (String) -> Unit
) {
    var selectedMood by rememberSaveable {
        mutableStateOf("")
    }

    var saved by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DateHeader()

        Spacer(Modifier.height(16.dp))

        Text(
            "How are you feeling today?",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(24.dp))

        listOf("Happy 😊", "Neutral 😐", "Sad 😢").forEach { mood ->
            FilterChip(
                selected = selectedMood == mood,
                onClick = {
                    selectedMood = mood
                    saved = false
                },
                label = { Text(mood) }
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            enabled = selectedMood.isNotEmpty(),
            onClick = {
                onSaveMood(selectedMood)
                saved = true
            }
        ) {
            Text("Save Mood")
        }

        if (saved) {
            Spacer(Modifier.height(16.dp))
            Text("Mood saved successfully!")
        }
    }
}
