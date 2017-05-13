#!/usr/bin/env kotlinc -script
/*
 *             Twidere - Twitter client for Android
 *
 *  Copyright (C) 2012-2017 Mariotaku Lee <mariotaku.lee@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

val files = File("src/main/svg/emoji").listFiles { file ->
    return@listFiles file.extension == "svg"
}

val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
val transformer = TransformerFactory.newInstance().newTransformer()

fun File.asDocument(): Document {
    return documentBuilder.parse(this)
}

files.forEach { file ->
    val document = file.asDocument()
    val svg = document.documentElement
    if (svg.getAttribute("width").isNullOrEmpty()) {
        svg.setAttribute("width", "45")
    }
    if (svg.getAttribute("height").isNullOrEmpty()) {
        svg.setAttribute("height", "45")
    }
    val source = DOMSource(document)
    transformer.transform(source, StreamResult(file))
}
