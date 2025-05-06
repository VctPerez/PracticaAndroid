package com.viewnext.practicaandroid.domain


/**
 * Parses a date string in the format "DD/MM/YYYY" to "YYYY-MM-DD".
 * @param date The date string in "DD/MM/YYYY" format.
 * @return The date string in "YYYY-MM-DD" format.
 */
fun parseDateToYYYYMMDD(date: String): String {
    if(!isDDMMYYYY(date)) {
        throw IllegalArgumentException("Invalid date format. Must be DD/MM/YYYY. Date Given: $date")
    }

    val dateParts = date.split("/")
    return "${dateParts[2]}-${dateParts[1]}-${dateParts[0]}"
}

/**
 * Parses a date string in the format "YYYY-MM-DD" to "DD/MM/YYYY".
 * @param date The date string in "YYYY-MM-DD" format.
 * @return The date string in "DD/MM/YYYY" format.
 */
fun parseDateFromYYYYMMDD(date: String): String {
    if (!isYYYYMMDD(date)) {
        throw IllegalArgumentException("Invalid date format. Must be YYYY-MM-DD. Date Given: $date")
    }

    val dateParts = date.split("-")
    return "${dateParts[2]}/${dateParts[1]}/${dateParts[0]}"
}

fun isYYYYMMDD(date: String): Boolean {
    return date.length == 10 && date[4] == '-' && date[7] == '-' &&
            date[0].isDigit() && date[1].isDigit() && date[2].isDigit() && date[3].isDigit() &&
            date[5].isDigit() && date[6].isDigit() &&
            date[8].isDigit() && date[9].isDigit()
}

fun isDDMMYYYY(date: String): Boolean {
    return date.length == 10 && date[2] == '/' && date[5] == '/' &&
            date[0].isDigit() && date[1].isDigit() &&
            date[3].isDigit() && date[4].isDigit() &&
            date[6].isDigit() && date[7].isDigit() &&
            date[8].isDigit() && date[9].isDigit()
}

