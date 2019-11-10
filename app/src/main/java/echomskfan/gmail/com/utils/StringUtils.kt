package echomskfan.gmail.com.utils

import java.util.*

/**
 * Converts string-typed date like "07 августа 2019, 21:05" to actual date
 */
fun String.toDate(): Date? {
    if (this.isBlank()) {
        return null
    }
    val part = this.split(" ")

    val dayOfMonth = part[0].toInt()
    val month = when (part[1]) {
        "января" -> 1
        "февраля" -> 2
        "марта" -> 3
        "апреля" -> 4
        "мая" -> 5
        "июня" -> 6
        "июля" -> 7
        "августа" -> 8
        "сентября" -> 9
        "октября" -> 10
        "ноября" -> 11
        "декабря" -> 12
        else -> 1
    }
    val year = part[2].dropLast(1).toInt()
    val hourOfDay = part[3].split(":")[0].toInt()
    val minute = part[3].split(":")[1].toInt()

    return GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute).time
}

