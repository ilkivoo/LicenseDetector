package ru.alyokhina.hse

import ru.alyokhina.hse.LicenseInfo
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList


class LicenseDetector(
    private val licenses: List<LicenseInfo>,
    private val fileNames: List<String>
) {
    public fun detectAllLicensed(dirPath: String): List<LicenseInfo> {
        val dirFile = File(dirPath)
        if (dirFile.exists()) {
            //TODO кидай в лог, что папка не найдена
        }
        if (!dirFile.isDirectory) {
            //TODO кидай в лог, что папка не найдена
        }
        if (dirFile.listFiles() == null) {
            return Collections.emptyList()
        }
        return Files.walk(dirFile.toPath())
            .map { path -> path.toFile() }
            .filter { file -> file.exists() && file.isFile }
            .flatMap { file -> getLicenseFromCodeFile(file).stream() }
            .collect(Collectors.toList())
    }


    public fun detectMainLicense(dirPath: String): Optional<LicenseInfo> {
        val dirFile = File(dirPath)
        if (dirFile.exists()) {
            //TODO кидай в лог, что папка не найдена
        }
        if (!dirFile.isDirectory) {
            //TODO кидай в лог, что папка не найдена
        }
        if (dirFile.listFiles() == null) {
            return Optional.empty()
        }
        val mainLicenses = File(dirPath).listFiles()!!
            .filter { file -> file.exists() && file.isFile && isMatchFileName(file) }
            .flatMap { file -> getLicense(file) }
        if (mainLicenses.isEmpty()) {
            return Optional.empty();
        }
        if (mainLicenses.size > 1) {
            //TODO главных лицензий много
        }
        return Optional.of(mainLicenses[0])
    }

    private fun isMatchFileName(file: File): Boolean {
        return fileNames.any() { fileName -> fileName.equals(file.name, true) }
    }

    private fun getLicense(file: File): List<LicenseInfo> {
        return licenses.filter { license ->
            val text = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).joinToString("\n")
            license.matchText(text)
        }.toCollection(ArrayList())
    }

    private fun getLicenseFromCodeFile(file: File): List<LicenseInfo> {
        val comments = getCommentBlocks(file)
        return licenses.filter { license ->
            comments.any { comment -> license.matchHeader(comment) }
        }.toCollection(ArrayList())
    }

}