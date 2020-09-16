package ru.alyokhina.hse.model

import java.util.regex.Pattern

class LicenseInfo(
    val fullName: String,
    val identifier: String,
    val textPattern: String,
    val headerPattern: String) {

}