package banquemisr.challenge05.data.repository.browsed

import androidx.paging.PagingData
import banquemisr.challenge05.data.model.movie.MovieDetails
import banquemisr.challenge05.data.model.movie.RecentlyBrowsedMovie
import kotlinx.coroutines.flow.Flow

interface RecentlyBrowsedRepository {
    fun addRecentlyBrowsedMovie(movieDetails: MovieDetails)

    fun clearRecentlyBrowsedMovies()

    fun clearRecentlyBrowsedTvShows()

    fun recentlyBrowsedMovies(): Flow<PagingData<RecentlyBrowsedMovie>>


}