package banquemisr.challenge05.ui.screens.details.movie

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import banquemisr.challenge05.data.model.*
import banquemisr.challenge05.data.model.movie.Movie
import banquemisr.challenge05.data.model.movie.MovieCollection
import banquemisr.challenge05.data.model.movie.MovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.util.*

@Stable
data class MovieDetailsScreenUIState(
    val startRoute: String,
    val movieDetails: MovieDetails?,
    val additionalMovieDetailsInfo: AdditionalMovieDetailsInfo,
    val error: String?
) {
    companion object {
        fun getDefault(startRoute: String): MovieDetailsScreenUIState {
            return MovieDetailsScreenUIState(
                startRoute = startRoute,
                movieDetails = null,
                additionalMovieDetailsInfo = AdditionalMovieDetailsInfo.default,
                error = null
            )
        }
    }
}

@Stable
data class AssociatedMovies(
    val collection: MovieCollection?,
    val similar: Flow<PagingData<Movie>>,
    val recommendations: Flow<PagingData<Movie>>,
    val directorMovies: DirectorMovies
) {
    companion object {
        val default: AssociatedMovies = AssociatedMovies(
            collection = null,
            similar = emptyFlow(),
            recommendations = emptyFlow(),
            directorMovies = DirectorMovies.default
        )
    }
}

@Stable
data class DirectorMovies(
    val directorName: String,
    val movies: Flow<PagingData<Movie>>
) {
    companion object {
        val default: DirectorMovies = DirectorMovies(
            directorName = "",
            movies = emptyFlow()
        )
    }
}

@Stable
data class AdditionalMovieDetailsInfo(
    val watchAtTime: Date?,
    val watchProviders: WatchProviders?,
    val reviewsCount: Int
) {
    companion object {
        val default: AdditionalMovieDetailsInfo = AdditionalMovieDetailsInfo(
            watchAtTime = null,
            watchProviders = null,
            reviewsCount = 0
        )
    }
}