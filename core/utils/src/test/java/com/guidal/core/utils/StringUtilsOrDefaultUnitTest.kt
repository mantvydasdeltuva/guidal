package com.guidal.core.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilsOrDefaultUnitTest {
    @Test
    fun `non-null non-blank string should return itself`() {
        val input = "Hello, World!"
        val default = "Default"
        val result = input.orDefault(default)
        assertEquals(
            "Expected the non-blank string to be returned.",
            "Hello, World!",
            result
        )
    }

    @Test
    fun `null string should return default`() {
        val input: String? = null
        val default = "Default"
        val result = input.orDefault(default)
        assertEquals(
            "Expected the default value to be returned for null input.",
            "Default",
            result
        )
    }

    @Test
    fun `empty string should return default`() {
        val input = ""
        val default = "Default"
        val result = input.orDefault(default)
        assertEquals(
            "Expected the default value to be returned for an empty string.",
            "Default",
            result
        )
    }

    @Test
    fun `blank string should return default`() {
        val input = "   "
        val default = "Default"
        val result = input.orDefault(default)
        assertEquals(
            "Expected the default value to be returned for a blank string.",
            "Default",
            result
        )
    }

    @Test
    fun `non-null non-blank string with special characters should return itself`() {
        val input = "@#$%&*"
        val default = "Default"
        val result = input.orDefault(default)
        assertEquals(
            "Expected the non-blank string with special characters to be returned.",
            "@#$%&*",
            result
        )
    }

    @Test
    fun `string with only tabs or newlines should return default`() {
        val input = "\n\t\n"
        val default = "Default"
        val result = input.orDefault(default)
        assertEquals(
            "Expected the default value to be returned for a string with only tabs or newlines.",
            "Default",
            result
        )
    }
}