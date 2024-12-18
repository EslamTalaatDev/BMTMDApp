package banquemisr.challenge05.domain.usecase.movie

import banquemisr.challenge05.data.model.DeviceLanguage
import banquemisr.challenge05.data.model.movie.MovieDetails
import banquemisr.challenge05.data.remote.api.ApiResponse
import banquemisr.challenge05.data.remote.api.awaitApiResponse
import banquemisr.challenge05.data.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        movieId: Int,
        deviceLanguage: DeviceLanguage
    ): ApiResponse<MovieDetails> {
        return movieRepository.movieDetails(
            movieId = movieId,
            isoCode = deviceLanguage.languageCode
        ).awaitApiResponse()
    }
}