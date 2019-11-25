package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val nameParts = fullName?.trim()?.split(" ")
        val firstName = nameParts?.getOrNull(0)
        val lastName = nameParts?.getOrNull(1)
        return Pair(
            if (firstName.isNullOrBlank()) null else firstName,
            if (lastName.isNullOrBlank()) null else lastName
        )
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val first = firstName?.trim()?.getOrNull(0)?.toUpperCase()
        val last = lastName?.trim()?.getOrNull(0)?.toUpperCase()
        if (first == null && last == null) return null
        return "${first ?: ""}${last ?: ""}"
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val map = mapOf(
            "а" to "a", "А" to "A",
            "б" to "b", "Б" to "B",
            "в" to "v", "В" to "V",
            "г" to "g", "Г" to "G",
            "д" to "d", "Д" to "D",
            "е" to "e", "Е" to "E",
            "ё" to "e", "Ё" to "E",
            "ж" to "zh", "Ж" to "Zh",
            "з" to "z", "З" to "Z",
            "и" to "i", "И" to "I",
            "й" to "i", "Й" to "I",
            "к" to "k", "К" to "K",
            "л" to "l", "Л" to "L",
            "м" to "m", "М" to "M",
            "н" to "n", "Н" to "N",
            "о" to "o", "О" to "O",
            "п" to "p", "П" to "P",
            "р" to "r", "Р" to "R",
            "с" to "s", "С" to "S",
            "т" to "t", "Т" to "T",
            "у" to "u", "У" to "U",
            "ф" to "f", "Ф" to "F",
            "х" to "h", "Х" to "H",
            "ц" to "c", "Ц" to "C",
            "ч" to "ch", "Ч" to "Ch",
            "ш" to "sh", "Ш" to "Sh",
            "щ" to "sh'", "Щ" to "Sh'",
            "ъ" to "", "Ъ" to "",
            "ы" to "i", "Ы" to "I",
            "ь" to "", "Ь" to "",
            "э" to "e", "Э" to "E",
            "ю" to "yu", "Ю" to "Yu",
            "я" to "ya", "Я" to "Ya"
        )
        val nameParts = payload.split(" ")

        return nameParts.joinToString(divider) {
            it.map { char ->
                map[char.toString()] ?: char
            }.joinToString("")
        }
    }
}
