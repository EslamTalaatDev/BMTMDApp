package banquemisr.challenge05.data.paging.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import banquemisr.challenge05.data.model.*
import banquemisr.challenge05.data.model.movie.Movie
import banquemisr.challenge05.data.remote.api.movie.TmdbMoviesApiHelper
import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException

class DiscoverMoviesPagingDataSource(
    private val apiMovieHelper: TmdbMoviesApiHelper,
    private val deviceLanguage: DeviceLanguage,
    private val genresParam: GenresParam = GenresParam(genres = emptyList()),
    private val onlyWithPosters: Boolean = false,
    private val releaseDateRange: DateRange
) : PagingSource<Int, Movie>() {
    private val fromReleaseDate = releaseDateRange.from?.let(::DateParam)
    private val toReleaseDate = releaseDateRange.to?.let(::DateParam)

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1

            val movieResponse = apiMovieHelper.discoverMovies(
                page = nextPage,
                isoCode = deviceLanguage.languageCode,
                region = deviceLanguage.region,
                genresParam = genresParam,
                fromReleaseDate = fromReleaseDate,
                toReleaseDate = toReleaseDate
            )

            val currentPage = movieResponse.page
            val totalPages = movieResponse.totalPages

            LoadResult.Page(
                data = movieResponse.movies
                    .filter { movie ->
                        if (onlyWithPosters) !movie.posterPath.isNullOrEmpty() else true
                    },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (currentPage + 1 > totalPages) null else currentPage + 1
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: JsonDataException) {
            LoadResult.Error(e)
        }
    }

}