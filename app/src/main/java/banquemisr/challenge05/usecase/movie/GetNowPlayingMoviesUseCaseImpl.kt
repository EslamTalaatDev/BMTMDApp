package banquemisr.challenge05.domain.usecase.movie

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import banquemisr.challenge05.data.model.DetailPresentable
import banquemisr.challenge05.data.model.DeviceLanguage
import banquemisr.challenge05.data.model.movie.MovieDetailEntity
import banquemisr.challenge05.data.repository.movie.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetNowPlayingMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository

) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        deviceLanguage: DeviceLanguage,
        filtered: Boolean
    ): Flow<PagingData<DetailPresentable>> {
        return movieRepository.nowPlayingMovies(deviceLanguage).mapLatest { data ->
            if (filtered) data.filterCompleteInfo() else data
        }.mapLatest { data -> data.map { it } }
    }

    private fun PagingData<MovieDetailEntity>.filterCompleteInfo(): PagingData<MovieDetailEntity> {
        return filter { movie ->
            movie.run {
                !backdropPath.isNullOrEmpty() &&
                        !posterPath.isNullOrEmpty() &&
                        title.isNotEmpty() &&
                        overview.isNotEmpty()
            }
        }
    }

}