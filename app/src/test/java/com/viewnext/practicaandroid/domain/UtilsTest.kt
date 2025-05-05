package com.viewnext.practicaandroid.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UtilsTest {

    @Nested
    inner class ParseDateToYYYYMMDDTest{
        @Test
        fun parseDateToYYYYMMDD_validDateFormat_returnsParsedDate() {
            val date = "01/10/2023"
            val expected = "2023-10-01"

            val result = parseDateToYYYYMMDD(date)

            assertEquals(result, expected)
        }

        @ParameterizedTest
        @ValueSource(strings = [
            "2023-10-01",
            "20/10-0101",
            "a0/10/2023",
            "0a/00/0000",
            "00/a0/0000",
            "00/0a/0000",
            "00/00/a000",
            "00/00/0a00",
            "00/00/00a0",
            "00/00/000a",
        ])
        fun parseDateToYYYYMMDD_invalidDateFormat_throwsException(date : String) {

            assertThrows<IllegalArgumentException>("Invalid date format", {
                parseDateToYYYYMMDD(date)
            })
        }

        @Test
        fun parseDateToYYYYMMDD_emptyString_throwsException() {
            val date = ""

            assertThrows<IllegalArgumentException>("Invalid date format", {
                parseDateToYYYYMMDD(date)
            })
        }

        // Test parseDateToYYYYMMDD function with null
        @Test
        fun parseDateToYYYYMMDD_null_throwsException() {
            val date: String? = null

            assertThrows<NullPointerException>("Invalid date format", {
                parseDateToYYYYMMDD(date!!)
            })
        }

        // Test parseDateToYYYYMMDD function with shorter and longer date strings
        @Test
        fun parseDateToYYYYMMDD_shorterDateString_throwsException() {
            val date = "1/10/2023"

            assertThrows<IllegalArgumentException>("Invalid date format", {
                parseDateToYYYYMMDD(date)
            })
        }

        @Test
        fun parseDateToYYYYMMDD_longerDateString_throwsException() {
            val date = "01/10/2023/2023"

            assertThrows<IllegalArgumentException>("Invalid date format", {
                parseDateToYYYYMMDD(date)
            })
        }

    }

    @Nested
    inner class ParseDateFromYYYYMMDDTest{
        // Test parseDateFromYYYYMMDD function
        @Test
        fun parseDateFromYYYYMMDD_validDateFormat_returnsParsedDate() {
            val date = "2023-10-01"
            val expected = "01/10/2023"

            val result = parseDateFromYYYYMMDD(date)

            assertEquals(result, expected)
        }

        @ParameterizedTest
        @ValueSource(strings = [
            "01/10/2023",
            "2023-10/01",
            "a000-00-00",
            "0a00-00-00",
            "00a0-00-00",
            "000a-00-00",
            "0000-a0-00",
            "0000-0a-00",
            "0000-00-a0",
            "0000-00-0a",
        ])
        fun parseDateFromYYYYMMDD_invalidDateFormat_throwsException(date: String) {
            assertThrows<IllegalArgumentException>("Invalid date format", {
                parseDateFromYYYYMMDD(date)
            })
        }

        // Test parseDateFromYYYYMMDD function with empty string
        @Test
        fun parseDateFromYYYYMMDD_emptyString_throwsException() {
            val date = ""

            assertThrows<IllegalArgumentException>("Invalid date format", {
                parseDateFromYYYYMMDD(date)
            })
        }

        // Test parseDateFromYYYYMMDD function with null
        @Test
        fun parseDateFromYYYYMMDD_null_throwsException() {
            val date: String? = null

            assertThrows<NullPointerException>("Invalid date format", {
                parseDateFromYYYYMMDD(date!!)
            })
        }

        // Test parseDateFromYYYYMMDD function with shorter and longer date strings
        @Test
        fun parseDateFromYYYYMMDD_shorterDateString_throwsException() {
            val date = "2023-10-1"

            assertThrows<IllegalArgumentException>("Invalid date format", {
                parseDateFromYYYYMMDD(date)
            })
        }

        @Test
        fun parseDateFromYYYYMMDD_longerDateString_throwsException() {
            val date = "2023-10-001"

            assertThrows<IllegalArgumentException>("Invalid date format", {
                parseDateFromYYYYMMDD(date)
            })
        }
    }


}