package banquemisr.challenge05.data.model

import banquemisr.challenge05.utils.formatted
import java.util.*

data class DateParam(private val date: Date) {
    override fun toString(): String {
        return date.formatted("yyyy-MM-dd")
    }
}
