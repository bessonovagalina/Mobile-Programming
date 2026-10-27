package com.example.astralite


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

fun getZodiacSign(day: Int, month: Int): String {
    return when (month) {
        1 -> if (day >= 20) "Aquarius ♒" else "Capricorn ♑"
        2 -> if (day >= 19) "Pisces ♓" else "Aquarius ♒"
        3 -> if (day >= 21) "Aries ♈" else "Pisces ♓"
        4 -> if (day >= 20) "Taurus ♉" else "Aries ♈"
        5 -> if (day >= 21) "Gemini ♊" else "Taurus ♉"
        6 -> if (day >= 21) "Cancer ♋" else "Gemini ♊"
        7 -> if (day >= 23) "Leo ♌" else "Cancer ♋"
        8 -> if (day >= 23) "Virgo ♍" else "Leo ♌"
        9 -> if (day >= 23) "Libra ♎" else "Virgo ♍"
        10 -> if (day >= 23) "Scorpio ♏" else "Libra ♎"
        11 -> if (day >= 22) "Sagittarius ♐" else "Scorpio ♏"
        12 -> if (day >= 22) "Capricorn ♑" else "Sagittarius ♐"
        else -> "Unknown"
    }
}

fun getDailyPrediction(): String {
    val predictions = listOf(
        "Today is a great day to start something new.",
        "A pleasant surprise may be waiting for you.",
        "Trust your intuition today.",
        "Take time to focus on yourself.",
        "An interesting opportunity may appear.",
        "A conversation could bring you inspiration.",
        "Small steps will lead to big achievements.",
        "Today is a good day for creativity."
    )

    return predictions.random()
}


@Composable
fun ZodiacScreen() {
    var day by rememberSaveable { mutableStateOf("") }
    var month by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("") }
    var prediction by rememberSaveable { mutableStateOf("") }

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
            text = "Discover Your Zodiac Sign",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = day,
            onValueChange = {
                day = it
                result = ""
                prediction = ""
            },
            label = { Text("Birth Day") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = month,
            onValueChange = {
                month = it
                result = ""
                prediction = ""
            },
            label = { Text("Birth Month (1-12)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                val d = day.toIntOrNull()
                val m = month.toIntOrNull()

                val maxDay = when (m) {
                    2 -> 29
                    4, 6, 9, 11 -> 30
                    in 1..12 -> 31
                    else -> 0
                }

                if (d != null && m != null &&
                    d in 1..maxDay
                ) {
                    result = getZodiacSign(d, m)
                    prediction = getDailyPrediction()
                } else {
                    result = "Please enter a valid date"
                    prediction = ""
                }
            }
        ) {
            Text("Find My Sign")
        }

        Spacer(Modifier.height(24.dp))

        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        if (prediction.isNotEmpty()) {
            Spacer(Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Your Daily Prediction ✨",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = prediction,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

