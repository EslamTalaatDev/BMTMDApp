package banquemisr.challenge05.utils

import kotlin.time.Duration.Companion.minutes

fun Int.formattedRuntime(): String? {
    return minutes.toComponents { hours, minutes, _, _ ->
        val hoursString = if (hours > 0) "${hours}h" else null
        val minutesString = if (minutes > 0) "${minutes}m" else null

        listOfNotNull(hoursString, minutesString).run {
            if (isEmpty()) null else joinToString(separator = " ")
        }
    }
}