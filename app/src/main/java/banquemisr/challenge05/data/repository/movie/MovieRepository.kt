package banquemisr.challenge05.data.repository.movie

import androidx.paging.PagingData
import banquemisr.challenge05.data.model.*
import banquemisr.challenge05.data.model.movie.Movie
import banquemisr.challenge05.data.model.movie.MovieDetailEntity
import banquemisr.challenge05.data.model.movie.MovieDetails
import banquemisr.challenge05.data.model.movie.MovieEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface MovieRepository {

    fun popularMovies(
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ): Flow<PagingData<MovieEntity>>

    fun upcomingMovies(
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ): Flow<PagingData<MovieEntity>>

    fun nowPlayingMovies(
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ): Flow<PagingData<MovieDetailEntity>>


    fun movieDetails(
        movieId: Int,
        isoCode: String = DeviceLanguage.default.languageCode
    ): Call<MovieDetails>




    fun collection(
        collectionId: Int,
        isoCode: String = DeviceLanguage.default.languageCode
    ): Call<CollectionResponse>

}