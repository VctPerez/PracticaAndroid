package com.viewnext.practicaandroid.domain

fun parseDateToYYYYMMDD(date: String): String {
    val dateParts = date.split("/")
    return "${dateParts[2]}-${dateParts[1]}-${dateParts[0]}"
}

fun parseDateFromYYYYMMDD(date: String): String {
    val dateParts = date.split("-")
    return "${dateParts[2]}/${dateParts[1]}/${dateParts[0]}"
}

