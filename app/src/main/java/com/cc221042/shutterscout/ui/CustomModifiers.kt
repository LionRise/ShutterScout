package com.cc221042.shutterscout.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.Dp

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