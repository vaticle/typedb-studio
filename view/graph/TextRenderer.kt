/*
 * Copyright (C) 2021 Vaticle
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.vaticle.typedb.studio.view.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import com.vaticle.typedb.studio.view.common.theme.Theme
import com.vaticle.typedb.studio.view.common.theme.Typography
import com.vaticle.typedb.studio.view.graph.TextRenderer.LineBreak.Reason.BlockStart
import com.vaticle.typedb.studio.view.graph.TextRenderer.LineBreak.Reason.Overflow
import com.vaticle.typedb.studio.view.graph.TextRenderer.LineBreak.Reason.Whitespace
import com.vaticle.typedb.studio.view.graph.TextRenderer.LineBreak.Reason.WordBreak
import com.vaticle.typedb.studio.view.material.Form
import org.jetbrains.skia.Font
import org.jetbrains.skia.TextLine
import java.util.concurrent.ConcurrentHashMap

internal class TextRenderer(private val viewport: Viewport) {

    private val _edgeLabelSizes = ConcurrentHashMap<String, DpSize>()
    val edgeLabelSizes: Map<String, DpSize> get() = _edgeLabelSizes

    fun drawSingleLine(drawScope: DrawScope, text: String, center: Offset, color: Color, typography: Typography.Theme) {
        val textLine = TextLine.make(
            text, Font(typography.fixedWidthSkiaTypeface, typography.codeSizeMedium * viewport.density)
        )
        drawScope.drawIntoCanvas {
            it.nativeCanvas.drawTextLine(
                textLine,
                center.x - textLine.width / 2,
                center.y + textLine.capHeight / 2,
                Paint().apply { this.color = color }.asFrameworkPaint()
            )
        }
    }

    fun DrawScope.drawVertexLabel(vertex: Vertex, color: Color, typography: Typography.Theme) {
        drawIntoCanvas {
            drawMultiLine(
                canvas = it,
                text = vertex.label,
                font = Font(typography.fixedWidthSkiaTypeface, typography.codeSizeMedium * density),
                center = with(viewport) { vertex.geometry.position.toViewport() },
                maxWidth = vertex.geometry.labelMaxWidth * density,
                maxLines = vertex.geometry.labelMaxLines(typography.codeSizeMedium),
                color = color
            )
        }
    }

    private fun drawMultiLine(
        canvas: Canvas,
        text: String,
        font: Font,
        center: Offset,
        maxWidth: Float,
        maxLines: Int,
        color: Color
    ) {
        val lines = mutableListOf<TextLine>()
        var remainingText = text.trim()
        while (lines.size < maxLines) {
            val remainingTextLine = TextLine.make(remainingText, font)
            val lineBreak = findLineBreak(remainingTextLine, remainingText, maxWidth)
            if (lineBreak == null) {
                lines += remainingTextLine
                break
            } else {
                val breakIndex = lineBreak.index - (if (lineBreak.reason == BlockStart) 1 else 0)
                lines += TextLine.make(remainingText.substring(0 until breakIndex), font)
                remainingText = remainingText.substring(breakIndex).trim()
            }
        }
        drawTextLines(canvas, lines, center, Paint().apply { this.color = color }.asFrameworkPaint())
    }

    private fun drawTextLines(canvas: Canvas, lines: List<TextLine>, center: Offset, paint: org.jetbrains.skia.Paint) {
        lines.forEachIndexed { index, line ->
            val yOffset = line.height * (0.5f * -(lines.size - 1) + index)
            canvas.nativeCanvas.drawTextLine(
                line, center.x - line.width / 2, center.y + line.capHeight / 2 + yOffset, paint
            )
        }
    }

    private fun findLineBreak(textLine: TextLine, text: String, lineMaxWidth: Float): LineBreak? {
        val lineOverflowIndex = textLine.positions
            .filterIndexed { idx, _ -> idx % 2 == 0 }
            .indexOfFirst { it > lineMaxWidth }
            .let { if (it == -1) null else (it - 1).coerceAtLeast(0) } ?: return null
        var lineEndIndex = lineOverflowIndex
        while (lineEndIndex > 0) {
            val char = text[lineEndIndex]
            if (char.isWhitespace()) return LineBreak(lineEndIndex, Whitespace)
            if (char.isWordBreakSymbol()) return LineBreak(lineEndIndex + 1, WordBreak)
            if (char.isBlockStartSymbol()) return LineBreak(lineEndIndex, BlockStart)
            lineEndIndex--
        }
        return LineBreak(index = lineOverflowIndex, Overflow)
    }

    private data class LineBreak(val index: Int, val reason: Reason) {
        enum class Reason {
            Whitespace,
            WordBreak,
            BlockStart,
            Overflow
        }
    }

    private fun Char.isWordBreakSymbol() = this in "-/|"

    private fun Char.isBlockStartSymbol() = this in "({["

    // TODO: get these metrics via drawSingleLine instead of a Composable?
    @Composable
    fun EdgeLabelMeasurer(edge: Edge) {
        with(LocalDensity.current) {
            Form.Text(
                value = edge.label, textStyle = Theme.typography.code1,
                modifier = Modifier.graphicsLayer(alpha = 0f).onSizeChanged {
                    _edgeLabelSizes[edge.label] = DpSize(it.width.toDp(), it.height.toDp())
                }
            )
        }
    }
}