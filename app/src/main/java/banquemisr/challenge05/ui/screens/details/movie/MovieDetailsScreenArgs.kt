package banquemisr.challenge05.ui.screens.details.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailsScreenArgs(
    val movieId: Int,
    val startRoute: String
) : Parcelable