package banquemisr.challenge05.data.repository.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import banquemisr.challenge05.data.local.db.AppDatabase
import banquemisr.challenge05.data.model.*
import banquemisr.challenge05.data.model.movie.*
import banquemisr.challenge05.data.paging.movie.*
import banquemisr.challenge05.data.remote.api.movie.TmdbMoviesApiHelper
import banquemisr.challenge05.data.remote.api.others.TmdbOthersApiHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val apiMovieHelper: TmdbMoviesApiHelper,
    private val appDatabase: AppDatabase,
    private val apiOtherHelper: TmdbOthersApiHelper
) : MovieRepository {


    override fun popularMovies(deviceLanguage: DeviceLanguage): Flow<PagingData<MovieEntity>> =
        Pager(
            PagingConfig(pageSize = 20),
            remoteMediator = MoviesRemotePagingMediator(
                deviceLanguage = deviceLanguage,
                apiMovieHelper = apiMovieHelper,
                appDatabase = appDatabase,
                type = MovieEntityType.Popular
            ),
            pagingSourceFactory = {
                appDatabase.moviesDao().getAllMovies(
                    type = MovieEntityType.Popular,
                    language = deviceLanguage.languageCode
                )
            }
        ).flow.flowOn(defaultDispatcher)

    override fun upcomingMovies(deviceLanguage: DeviceLanguage): Flow<PagingData<MovieEntity>> =
        Pager(
            PagingConfig(pageSize = 20),
            remoteMediator = MoviesRemotePagingMediator(
                deviceLanguage = deviceLanguage,
                apiMovieHelper = apiMovieHelper,
                appDatabase = appDatabase,
                type = MovieEntityType.Upcoming
            ),
            pagingSourceFactory = {
                appDatabase.moviesDao().getAllMovies(
                    type = MovieEntityType.Upcoming,
                    language = deviceLanguage.languageCode
                )
            }
        ).flow.flowOn(defaultDispatcher)



    override fun nowPlayingMovies(deviceLanguage: DeviceLanguage): Flow<PagingData<MovieDetailEntity>> =
        Pager(
            PagingConfig(pageSize = 20),
            remoteMediator = MovieDetailsPagingRemoteMediator(
                apiMovieHelper = apiMovieHelper,
                deviceLanguage = deviceLanguage,
                appDatabase = appDatabase,
            ),
            pagingSourceFactory = {
                appDatabase.moviesDetailsDao().getAllMovies(
                    language = deviceLanguage.languageCode
                )
            }
        ).flow.flowOn(defaultDispatcher)


    override fun movieDetails(movieId: Int, isoCode: String): Call<MovieDetails> {
        return apiMovieHelper.getMovieDetails(movieId, isoCode)
    }


    override fun collection(collectionId: Int, isoCode: String): Call<CollectionResponse> {
        return apiOtherHelper.getCollection(collectionId, isoCode)
    }

}