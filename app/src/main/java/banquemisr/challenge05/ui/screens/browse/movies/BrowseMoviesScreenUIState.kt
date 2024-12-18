package banquemisr.challenge05.ui.screens.browse.movies

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import banquemisr.challenge05.data.model.Presentable
import banquemisr.challenge05.data.model.movie.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
data class BrowseMoviesScreenUIState(
    val selectedMovieType: MovieType,
    val movies: Flow<PagingData<Presentable>>,
) {
    companion object {
        fun getDefault(selectedMovieType: MovieType): BrowseMoviesScreenUIState {
            return BrowseMoviesScreenUIState(
                selectedMovieType = selectedMovieType,
                movies = emptyFlow(),
            )
        }
    }
}