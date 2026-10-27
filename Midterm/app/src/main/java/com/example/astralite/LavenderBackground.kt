
package com.example.astralite

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun LavenderBackground(
    content: @Composable () -> Unit
) {
    val transition = rememberInfiniteTransition(
        label = "stars"
    )

    val glow by transition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = EaseInOutSine
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "starGlow"
    )

    Box(modifier = Modifier.fillMaxSize()) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            // Light lavender background
            drawRect(Color(0xFFE8DDF5))

            val stars = listOf(
                0.12f to 0.10f,
                0.75f to 0.15f,
                0.42f to 0.23f,
                0.88f to 0.35f,
                0.15f to 0.43f,
                0.65f to 0.52f,
                0.30f to 0.65f,
                0.84f to 0.73f,
                0.13f to 0.85f,
                0.57f to 0.91f
            )

            stars.forEachIndexed { index, star ->

                val center = Offset(
                    size.width * star.first,
                    size.height * star.second
                )

                val alpha = if (index % 2 == 0) { // мигают в зависимости от индекса
                    glow
                } else {
                    0.75f
                }

                drawStar( // каждая третья звезда больше остальных
                    center = center,
                    radius = if (index % 3 == 0) {
                        9.dp.toPx()
                    } else {
                        5.dp.toPx()
                    },
                    alpha = alpha
                )
            }
        }

        content()
    }
}

private fun DrawScope.drawStar(
    center: Offset,
    radius: Float,
    alpha: Float
) {
    // Soft outer glow
    drawCircle(
        color = Color.White,
        radius = radius * 2.5f,
        center = center,
        alpha = alpha * 0.12f
    )

    drawCircle(
        color = Color.White,
        radius = radius * 1.5f,
        center = center,
        alpha = alpha * 0.22f
    )

    // Four-point star
    val path = Path().apply {
        moveTo(center.x, center.y - radius)
        lineTo(center.x + radius * 0.2f, center.y - radius * 0.2f)
        lineTo(center.x + radius, center.y)
        lineTo(center.x + radius * 0.2f, center.y + radius * 0.2f)
        lineTo(center.x, center.y + radius)
        lineTo(center.x - radius * 0.2f, center.y + radius * 0.2f)
        lineTo(center.x - radius, center.y)
        lineTo(center.x - radius * 0.2f, center.y - radius * 0.2f)
        close()
    }

    drawPath(
        path = path,
        color = Color.White,
        alpha = alpha
    )

    // Bright center
    drawCircle(
        color = Color.White,
        radius = radius * 0.18f,
        center = center,
        alpha = alpha
    )
}
