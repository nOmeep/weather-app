package com.example.weatherreport.util.funs

import org.junit.Test
import com.google.common.truth.Truth.assertThat

class CapitalizeFirstLetterTest {

    @Test
    fun `check if first letter will become capitalized when string is not empty`() {
        val str = "string"
        val result = str.capitalizeFirstLetter()

        assertThat(result).isEqualTo("String")
    }

    @Test
    fun `check that nothing will happen when string is empty`() {
        val emptyString = ""
        val result = emptyString.capitalizeFirstLetter()

        assertThat(result).isEqualTo("")
    }
}