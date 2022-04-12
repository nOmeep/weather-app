package com.example.weatherreport.util.funs

import org.junit.Test
import com.google.common.truth.Truth.assertThat

class ConcatToStringWithSeparatorTest {

    @Test
    fun `check if string concat to string`() {
        val pair = ("1" to "2").concatToStringWithSeparator(",")

        assertThat(pair).isEqualTo("1,2")
    }

    @Test
    fun `check if float concat to string`() {
        val pair = (1.0003f to "something").concatToStringWithSeparator(",")

        assertThat(pair).isEqualTo("1.0003,something")
    }
}