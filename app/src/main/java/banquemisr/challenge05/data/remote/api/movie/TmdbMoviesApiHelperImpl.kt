package banquemisr.challenge05.data.remote.api.movie

import banquemisr.challenge05.data.model.*
import banquemisr.challenge05.data.model.movie.MovieDetails
import banquemisr.challenge05.data.model.movie.MoviesResponse
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TmdbMoviesApiHelperImpl @Inject constructor(
    private val tmdbMoviesApi: TmdbMoviesApi
) : TmdbMoviesApiHelper {
    override fun getConfig(): Call<Config> {
        return tmdbMoviesApi.getConfig()
    }

    override suspend fun discoverMovies(
        page: Int,
        isoCode: String,
        region: String,
        genresParam: GenresParam,
        watchProvidersParam: WatchProvidersParam,
        voteRange: ClosedFloatingPointRange<Float>,
        fromReleaseDate: DateParam?,
        toReleaseDate: DateParam?
    ): MoviesResponse {
        return tmdbMoviesApi.discoverMovies(
            page,
            isoCode,
            region,
            genresParam,
            watchProvidersParam,
            voteAverageMin = voteRange.start,
            voteAverageMax = voteRange.endInclusive,
            fromReleaseDate = fromReleaseDate,
            toReleaseDate = toReleaseDate
        )
    }

    override suspend fun getPopularMovies(
        page: Int,
        isoCode: String,
        region: String
    ): MoviesResponse {
        return tmdbMoviesApi.getPopularMovies(page, isoCode, region)
    }

    override suspend fun getUpcomingMovies(
        page: Int,
        isoCode: String,
        region: String
    ): MoviesResponse {
        return tmdbMoviesApi.getUpcomingMovies(page, isoCode, region)
    }



    override suspend fun getNowPlayingMovies(
        page: Int,
        isoCode: String,
        region: String
    ): MoviesResponse {
        return tmdbMoviesApi.getNowPlayingMovies(page, isoCode, region)
    }

    override fun getMovieDetails(movieId: Int, isoCode: String): Call<MovieDetails> {
        return tmdbMoviesApi.getMovieDetails(movieId, isoCode)
    }



}