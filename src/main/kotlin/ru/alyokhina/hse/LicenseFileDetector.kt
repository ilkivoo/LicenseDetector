package ru.alyokhina.hse

import ru.alyokhina.hse.model.LicenseInfo
import java.io.File
import java.util.*


class LicenseFileDetector(
    val licenses: List<LicenseInfo>,
    val fileNameRegexs: List<Regex>
) {

    public fun detectMainLicense(dirPath: String): Optional<LicenseInfo> {
        val dirFile = File(dirPath)
        if (dirFile.exists()) {
            //TODO кидай в лог, что папка не найдена
        }
        if (!dirFile.isDirectory) {
            //TODO кидай в лог, что папка не найдена
        }
        if (dirFile.listFiles() == null) {
            return Optional.empty();
        }
        File(dirPath).listFiles()!!
            .filter { file -> file.isFile
                    && fileNameRegexs.any(){ fileNameRegex -> fileNameRegex.matches(file.name)}}
            .forEach { file -> println(file.name) }
        return Optional.empty()

    }


}