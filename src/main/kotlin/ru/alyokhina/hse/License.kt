package ru.alyokhina.hse

import com.google.common.collect.ImmutableList
import java.util.*

data class LicenseInfo(
    val fullName: String,
    val identifier: String,
    val fullText: String,
    val headers: List<String>
) {
    public fun matchText(text: String): Boolean {
        if (text.isEmpty()) {
            return false
        }
        val processedText = processedFromText(text)
        return processedText.contains(fullText, true)
    }

    public fun matchHeader(comment: String): Boolean {
        if (comment.isEmpty()) {
            return false
        }
        val processedText = processedFromText(comment)

        return headers.any { header -> processedText.contains(header, true) }
    }
}


fun createLicenseBase(): List<LicenseInfo> {
    return Collections.singletonList(apache2())
}

fun createValidLicenseFileNames(): List<String> {
    return ImmutableList.of("LICENSE", "LICENSE.md")
}

fun apache2(): LicenseInfo {
    val fullName = "Apache License 2.0"
    val identifier = "Apache-2.0"
    val fullTexPath = "src/main/resources/licenses/text/apache2_0.txt"
    val header1 = "src/main/resources/licenses/header/header_apache2_0_v1.txt"
    val header2 = "src/main/resources/licenses/header/header_apache2_0_v2.txt"
    return LicenseInfo(
        fullName,
        identifier,
        processedFromFile(filePath = fullTexPath),
        ImmutableList.of(
            processedFromFile(filePath = header1),
            processedFromFile(filePath = header2)
        )
    )
}