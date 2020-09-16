package ru.alyokhina.hse

import com.github.javaparser.JavaParser
import com.github.javaparser.ParseResult
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.comments.Comment
import java.io.File
import java.io.FileReader
import java.io.Reader
import java.io.StringReader
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

fun getCommentBlocks(file: File): List<String> {
    val codeText = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)
        .filter { line -> line.isNotEmpty() }
        .joinToString("\n")
    val textReader = StringReader(codeText);

    val compilationUnit = JavaParser().parse(textReader);

    if (compilationUnit == null
        || !compilationUnit.commentsCollection.isPresent
        || !compilationUnit.isSuccessful
    ) {
        return Collections.emptyList()
    }
    val commentBlockBuilder = StringBuilder()
    var lastCommentLine = 0
    val result = ArrayList<String>()
    for (comment in compilationUnit.commentsCollection.get().comments) {
        if (comment != null
            && comment.tokenRange.isPresent
            && comment.tokenRange.get().begin.range.isPresent
            && comment.tokenRange.get().end.range.isPresent
        ) {
            val beginLine = comment.tokenRange.get().begin.range.get().begin.line
            val endLine = comment.tokenRange.get().end.range.get().end.line
            if (lastCommentLine + 1 != beginLine
                && commentBlockBuilder.isNotEmpty()
            ) {
                result.add(commentBlockBuilder.toString())
                commentBlockBuilder.clear()
            }
            commentBlockBuilder.append(getCommentContent(comment))
                .append("\n")
            lastCommentLine = endLine
        }
    }
    if (!commentBlockBuilder.isEmpty()) {
        result.add(commentBlockBuilder.toString())
        commentBlockBuilder.clear()
    }
    return result
}

fun getCommentContent(comment: Comment): String {
    return comment.content.split("\n")
        .joinToString("\n") { line ->
            line.replace(Regex("^\\s*\\*\\s*"), "")
                .replace(Regex("\\s*\\*\\s*$"), "")
                .replace(Regex("^\\s*\\/\\*\\s*"), "")
                .replace(Regex("\\s*\\*\\/\\s*$"), "")
        }
}

