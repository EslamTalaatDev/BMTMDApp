package banquemisr.challenge05.ui.screens.browse.movies

import android.os.Parcelable
import banquemisr.challenge05.data.model.movie.MovieType
import kotlinx.parcelize.Parcelize

@Parcelize
data class BrowseMoviesScreenArgs(
    val movieType: MovieType
) : Parcelable