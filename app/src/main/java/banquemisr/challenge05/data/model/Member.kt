package banquemisr.challenge05.data.model

import androidx.compose.runtime.Stable

@Stable
interface Member {
    val id: Int
    val profilePath: String?
    val firstLine: String?
    val secondLine: String?
}