package ru.skillbranch.devintensive.extensions

import java.util.regex.Pattern


fun String.truncate(count: Int = 16): String {
    val result = this.trim()
    if (result.length <= count) return result
    return result.substring(0, count).trim().plus("...")
}

fun String.stripHtml(): String {
    return Pattern
            .compile("<[^>]*>")
            .matcher(this)
            .replaceAll("")
            .replace("  ", "")
}
