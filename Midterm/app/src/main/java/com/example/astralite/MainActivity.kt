
package com.example.astralite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                AstraApp()
            }
        }
    }
}

@Composable
fun AstraApp() {
    val navController = rememberNavController()

    val astroViewModel: AstroViewModel = viewModel() // управляет историей настроейний

    val moodHistory by astroViewModel.moodHistory
        .collectAsStateWithLifecycle()

    LavenderBackground {
        Scaffold(
            containerColor = Color.Transparent,

            bottomBar = {
                NavigationBar {
                    listOf(
                        "Zodiac" to "zodiac",
                        "Mood" to "mood",
                        "History" to "history"
                    ).forEach { (title, route) ->

                        NavigationBarItem(
                            selected = false,

                            onClick = {
                                navController.navigate(route) {
                                    launchSingleTop = true
                                    popUpTo("zodiac") {
                                        saveState = true
                                    }
                                    restoreState = true
                                }
                            },

                            icon = {
                                Text("✦")
                            },

                            label = {
                                Text(title)
                            }
                        )
                    }
                }
            }
        ) { padding ->

            NavHost(
                navController = navController,
                startDestination = "zodiac",
                modifier = Modifier.padding(padding)
            ) {

                composable("zodiac") {
                    ZodiacScreen()
                }

                composable("mood") {
                    MoodScreen(
                        onSaveMood = astroViewModel::saveMood
                    )
                }

                composable("history") {
                    HistoryScreen(
                        entries = moodHistory,
                        onDeleteMood = astroViewModel::deleteMood
                    )
                }
            }
        }
    }
}
