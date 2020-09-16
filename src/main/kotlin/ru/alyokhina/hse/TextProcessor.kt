package ru.alyokhina.hse

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList


fun processedFromFile(
    filePath: String
): String {
    return processTextLines(Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8))
        .joinToString(" ")
}

fun processedFromText(
    text: String?
): String {
    if (text == null) {
        return "";
    }
    val lines = text.split("\n");
    return processTextLines(lines).joinToString(" ");
}


fun processTextLines(lines: List<String>): List<String> {
    return lines.map { line -> processTextLine(line) }
        .filter { processedLineOpt -> processedLineOpt.isPresent }
        .map { processedLineOpt -> processedLineOpt.get() }
        .toCollection(ArrayList());
}

fun processTextLine(line: String): Optional<String> {
    val newLine = line.trim().replace(Regex("\\s+"), " ")
    return if (newLine.isEmpty())
        Optional.empty()
    else
        Optional.of(newLine);
}
