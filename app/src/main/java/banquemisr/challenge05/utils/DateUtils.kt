package banquemisr.challenge05.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatted(format: String = "dd.MM.yyyy"): String {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.format(this)
}

fun Date.timeString() = formatted(format = "HH:mm")

