
package com.example.astralite

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DateHeader() {
    val today = SimpleDateFormat(
        "EEEE, dd MMMM yyyy",
        Locale.ENGLISH
    ).format(Date())

    Text(
        text = today,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary
    )
}
