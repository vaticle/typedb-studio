/*
 * Copyright (C) 2022 Vaticle
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

package com.vaticle.typedb.studio.framework.editor

import androidx.compose.ui.text.AnnotatedString
import kotlin.streams.toList

internal class GlyphLine constructor(val annotatedString: AnnotatedString) {

    val text = annotatedString.text
    val codepoints = annotatedString.codePoints().toList()
    val length = codepoints.size
    fun isEmpty(): Boolean = length == 0

    fun subSequence(start: Int, end: Int): GlyphLine {
        val codepoints = annotatedString.codePoints().toList()
        val codepointSubsequence = codepoints.subList(start, end)
        val text = codepointSubsequence.joinToString("") { Character.toString(it) }
        return GlyphLine(AnnotatedString(text))
    }

    fun subSequenceSafely(start: Int, end: Int): GlyphLine {
        if (isEmpty()) return GlyphLine(AnnotatedString(""))
        val coercedStart = start.coerceIn(0, length)
        val coercedEnd = end.coerceIn(coercedStart, length)
        return this.subSequence(coercedStart, coercedEnd)
    }

    fun getOffset(offset: Int): Int {
        return if (offset > length) {
            getOffset(length)
        } else {
            var count = 0
            for (code in codepoints.subList(0, offset)) {
                count += if (code >= 65535) {
                    2
                } else {
                    1
                }
            }
            count
        }
    }

    fun offsetToOffset(offset: Int): Int {
        var offset = offset
        var offset2 = 0
        for (code in codepoints) {
            if (code >= 65535) {
                offset -= 2
            } else {
                offset -= 1
            }
            offset2 += 1
            if (offset == 0) {
                return offset2
            }
        }
        return 0
    }

    class Glyph(val codePoint: Int) {
        // Characters past 65535 (U+FFFF) are double-width, and exceed the 16-bit size that can fit in a Kotlin `Char`.
        val doubleWidth: Boolean = codePoint >= 65535
    }
}