package banquemisr.challenge05.domain.usecase.movie

import banquemisr.challenge05.data.model.DeviceLanguage
import banquemisr.challenge05.data.model.movie.MovieCollection
import banquemisr.challenge05.data.remote.api.ApiResponse
import banquemisr.challenge05.data.remote.api.awaitApiResponse
import banquemisr.challenge05.data.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieCollectionUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        collectionId: Int,
        deviceLanguage: DeviceLanguage
    ): ApiResponse<MovieCollection?> {
        val response = movieRepository.collection(
            collectionId = collectionId,
            isoCode = deviceLanguage.languageCode
        ).awaitApiResponse()

        return when (response) {
            is ApiResponse.Success -> {
                val collection = response.data?.let { collection ->
                    val name = collection.name
                    val parts = collection.parts

                    MovieCollection(
                        name = name,
                        parts = parts
                    )
                }
                ApiResponse.Success(collection)
            }
            is ApiResponse.Failure -> ApiResponse.Failure(response.apiError)
            is ApiResponse.Exception -> ApiResponse.Exception(response.exception)
        }
    }

}