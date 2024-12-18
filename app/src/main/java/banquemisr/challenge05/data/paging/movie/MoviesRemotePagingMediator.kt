package banquemisr.challenge05.data.paging.movie

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import banquemisr.challenge05.data.local.db.AppDatabase
import banquemisr.challenge05.data.model.DeviceLanguage
import banquemisr.challenge05.data.model.movie.MovieEntity
import banquemisr.challenge05.data.model.movie.MovieEntityType
import banquemisr.challenge05.data.model.movie.MoviesRemoteKeys
import banquemisr.challenge05.data.model.movie.MoviesResponse
import banquemisr.challenge05.data.remote.api.movie.TmdbMoviesApiHelper
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesRemotePagingMediator(
    private val deviceLanguage: DeviceLanguage,
    private val type: MovieEntityType,
    private val apiMovieHelper: TmdbMoviesApiHelper,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, MovieEntity>() {
    private val movieCacheDao = appDatabase.moviesDao()
    private val remoteKeysDao = appDatabase.moviesRemoteKeysDao()

    private val apiMovieHelperMethod: suspend (Int, String, String) -> MoviesResponse =
        when (type) {
            MovieEntityType.Discover -> apiMovieHelper::discoverMovies
            MovieEntityType.Upcoming -> apiMovieHelper::getUpcomingMovies
            MovieEntityType.Popular -> apiMovieHelper::getPopularMovies
        }

    override suspend fun initialize(): InitializeAction {
        val remoteKey = appDatabase.withTransaction {
            remoteKeysDao.getRemoteKey(
                type = type,
                language = deviceLanguage.languageCode
            )
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if ((System.currentTimeMillis() - remoteKey.lastUpdated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKey = appDatabase.withTransaction {
                        remoteKeysDao.getRemoteKey(
                            type = type,
                            language = deviceLanguage.languageCode
                        )
                    } ?: return MediatorResult.Success(true)

                    if (remoteKey.nextPage == null) {
                        return MediatorResult.Success(true)
                    }

                    remoteKey.nextPage
                }
            }

            val result =
                apiMovieHelperMethod(page, deviceLanguage.languageCode, deviceLanguage.region)

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieCacheDao.deleteMoviesOfType(
                        type = type,
                        language = deviceLanguage.languageCode
                    )
                    remoteKeysDao.deleteRemoteKeysOfType(
                        type = type,
                        language = deviceLanguage.languageCode
                    )
                }

                val nextPage = if (result.movies.isNotEmpty()) {
                    page + 1
                } else null

                val movieEntities = result.movies.map { movie ->
                    MovieEntity(
                        id = movie.id,
                        type = type,
                        title = movie.title,
                        originalTitle = movie.originalTitle,
                        posterPath = movie.posterPath,
                        language = deviceLanguage.languageCode,
                        releaseDate = movie.releaseDate.toString()
                    )
                }

                remoteKeysDao.insertKey(
                    MoviesRemoteKeys(
                        language = deviceLanguage.languageCode,
                        type = type,
                        nextPage = nextPage,
                        lastUpdated = System.currentTimeMillis()
                    )
                )
                movieCacheDao.addMovies(movieEntities)
            }
            MediatorResult.Success(
                endOfPaginationReached = result.movies.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: JsonDataException) {
            MediatorResult.Error(e)
        }
    }
}