package ru.alyokhina.hse

import ru.alyokhina.hse.model.LicenseInfo
import java.util.*


// в корневой папке должны быть вайлы LICENSE или LICENSE.md
// в каждом файле смотреть заголовок
fun main() {
    val s: String;
    val apache2_0 = LicenseInfo("Apache License 2.0",
    "Apache-2.0",
    "123",
    "123")
    val licenseFileDetector =  LicenseFileDetector(Collections.singletonList(apache2_0),
    Collections.singletonList(Regex("1\\.txt")))
    licenseFileDetector.detectMainLicense("/Users/alyokhina-o/LicenseDetector/src/main/kotlin/ru/alyokhina/hse")
}

