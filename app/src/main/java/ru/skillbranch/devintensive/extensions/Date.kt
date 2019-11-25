package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

private const val SECOND = 1000L
private const val MINUTE = 60 * SECOND
private const val HOUR = 60 * MINUTE
private const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, unit: TimeUnits): Date {
    var time = this.time
    time += when (unit) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    return Date(time)
}

fun Date.humanizeDiff(date: Date): String {
    val diff = date.time - this.time
    return when (diff.absoluteValue) {
        in 0..SECOND -> "только что"
        in SECOND..45 * SECOND ->
            if (diff < 0) "через несколько секунд"
            else "несколько секунд назад"
        in 45 * SECOND..75 * SECOND ->
            if (diff < 0) "через минуту"
            else "минуту назад"
        in 75 * SECOND..45 * MINUTE ->
            if (diff < 0) "через ${TimeUnits.MINUTE.plural((diff / MINUTE).toInt())}"
            else "${TimeUnits.MINUTE.plural((diff / MINUTE).toInt())} назад"
        in 45 * MINUTE..75 * MINUTE ->
            if (diff < 0) "через час"
            else "час назад"
        in 75 * MINUTE..22 * HOUR ->
            if (diff < 0) "через ${TimeUnits.HOUR.plural((diff / HOUR).toInt())}"
            else "${TimeUnits.HOUR.plural((diff / HOUR).toInt())} назад"
        in 22 * HOUR..26 * HOUR ->
            if (diff < 0) "через день"
            else "день назад"
        in 26 + HOUR..360 * DAY ->
            if (diff < 0) "через ${TimeUnits.DAY.plural((diff / DAY).toInt())}"
            else "${TimeUnits.DAY.plural((diff / DAY).toInt())} назад"
        else ->
            if (diff < 0) "более чем через год"
            else "более года назад"
    }
}

enum class TimeUnits {

    SECOND {
        override fun plural(value: Int): String {
            return when (calcCount(value)) {
                1 -> "${value.absoluteValue} секунду"
                in 2..4 -> "${value.absoluteValue} секунды"
                else -> "${value.absoluteValue} секунд"
            }
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            return when (calcCount(value)) {
                1 -> "${value.absoluteValue} минуту"
                in 2..4 -> "${value.absoluteValue} минуты"
                else -> "${value.absoluteValue} минут"
            }
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            return when (calcCount(value)) {
                1 -> "${value.absoluteValue} час"
                in 2..4 -> "${value.absoluteValue} часа"
                else -> "${value.absoluteValue} часов"
            }
        }
    },
    DAY {
        override fun plural(value: Int): String {
            return when (calcCount(value)) {
                1 -> "${value.absoluteValue} день"
                in 2..4 -> "${value.absoluteValue} дня"
                else -> "${value.absoluteValue} дней"
            }
        }
    };

    abstract fun plural(value: Int): String

    fun calcCount(value: Int): Int {
        var count = value.absoluteValue
        while (count > 19) {
            count %= if (count > 100) 100 else 10
        }
        return count
    }
}
