package com.guidal.core.utils

/**
 * Module :core:utils
 *
 * Extension function for nullable String that provides a default value when the string is null or blank.
 *
 * @param default The default string value to return if the original string is null or blank.
 * @return The original string if it's non-null and not blank, or the default value if the string is null or blank.
 *
 * ‎
 *
 * **Best Practices**
 * - Use this extension to simplify handling of nullable strings and to avoid verbose null or blank checks.
 * - Improves readability and ensures consistent behavior for null or blank string values.
 */
fun String?.orDefault(default: String): String = this?.ifBlank { default } ?: default