package com.cc221042.shutterscout.ui

import android.graphics.Path
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



enum class Corner {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
}

fun Modifier.gradientBackground(
    colors: List<Color>,
    cornerRadius: Dp,
    startCorner: Corner = Corner.TOP_LEFT,
    endCorner: Corner = Corner.BOTTOM_RIGHT
) = this.then(
    Modifier.drawBehind {
        val startOffset = cornerToOffset(size, startCorner)
        val endOffset = cornerToOffset(size, endCorner)

        // Clip the drawing area to round the corners
        clipRect {
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = colors,
                    start = startOffset,
                    end = endOffset
                ),
                size = size,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }
    }
)

private fun cornerToOffset(size: Size, corner: Corner): Offset = when (corner) {
    Corner.TOP_LEFT -> Offset(0f, 0f)
    Corner.TOP_RIGHT -> Offset(size.width, 0f)
    Corner.BOTTOM_LEFT -> Offset(0f, size.height)
    Corner.BOTTOM_RIGHT -> Offset(size.width, size.height)
}


fun Modifier.customShadowLayered(
    color: Color = Color.Black,
    offsetX: Dp = 4.dp,
    offsetY: Dp = 4.dp,
    blurRadius: Dp = 10.dp
): Modifier = drawBehind {
    val shadowColor = color.copy(alpha = 0.2f) // Start with a more transparent color
    val dx = offsetX.toPx()
    val dy = offsetY.toPx()
    val radius = blurRadius.toPx()
    val steps = 5 // Number of layers

    // Draw multiple layers, decreasing size and increasing transparency
    for (i in 1..steps) {
        val currentAlpha = shadowColor.alpha * (steps - i + 1) / steps
        val currentColor = shadowColor.copy(alpha = currentAlpha)
        drawCircle(
            color = currentColor,
            radius = radius - (i * dx / steps),
            center = Offset(size.width / 2 + dx - (i * dx / steps), size.height / 2 + dy - (i * dy / steps))
        )
    }
}