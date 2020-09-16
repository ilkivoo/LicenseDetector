package ru.alyokhina.hse

fun main() {
    val licenseBase = createLicenseBase()
    val validFileNames = createValidLicenseFileNames()
    val detector = LicenseDetector(licenseBase, validFileNames)
    println("Введите путь до дириктории проекта")
    val dirPath = readLine()
    if (dirPath == null) {
        println("некорректный ввод")
        return
    }
    val mainLicenseOpt = detector.detectMainLicense(dirPath)
    val allLicenses = detector.detectAllLicensed(dirPath)

    if (mainLicenseOpt.isPresent) {
        println("Основная лицензия: ${mainLicenseOpt.get().fullName}")
    }
    else {
        println("У данного проекта нет основной лицензии")
    }

    if (allLicenses.isEmpty()) {
        println("У данного проекта нет лицензий, указанных в коде")
    }
    else {
        println("Найдено ${allLicenses.size} лицензий в коде" )
        for (license in allLicenses) {
            println(license.fullName)
        }
    }

}



