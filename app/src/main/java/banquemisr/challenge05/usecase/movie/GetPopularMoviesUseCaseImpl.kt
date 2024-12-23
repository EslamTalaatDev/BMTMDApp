package banquemisr.challenge05.domain.usecase.movie

import androidx.paging.PagingData
import androidx.paging.map
import banquemisr.challenge05.data.model.DeviceLanguage
import banquemisr.challenge05.data.model.Presentable
import banquemisr.challenge05.data.repository.movie.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject


class GetPopularMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(deviceLanguage: DeviceLanguage): Flow<PagingData<Presentable>> {
        return movieRepository.popularMovies(
            deviceLanguage
        ).mapLatest { data -> data.map { it } }
    }
}