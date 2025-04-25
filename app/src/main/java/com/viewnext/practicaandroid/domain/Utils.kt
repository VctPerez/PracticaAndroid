package com.viewnext.practicaandroid.domain


/**
 * Parses a date string in the format "DD/MM/YYYY" to "YYYY-MM-DD".
 * @param date The date string in "DD/MM/YYYY" format.
 * @return The date string in "YYYY-MM-DD" format.
 */
fun parseDateToYYYYMMDD(date: String): String {
    val dateParts = date.split("/")
    return "${dateParts[2]}-${dateParts[1]}-${dateParts[0]}"
}

/**
 * Parses a date string in the format "YYYY-MM-DD" to "DD/MM/YYYY".
 * @param date The date string in "YYYY-MM-DD" format.
 * @return The date string in "DD/MM/YYYY" format.
 */
fun parseDateFromYYYYMMDD(date: String): String {
    val dateParts = date.split("-")
    return "${dateParts[2]}/${dateParts[1]}/${dateParts[0]}"
}

