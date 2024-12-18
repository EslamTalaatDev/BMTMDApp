package banquemisr

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import banquemisr.challenge05.data.local.db.AppDatabase
import banquemisr.challenge05.data.local.db.movie.MoviesDao
import banquemisr.challenge05.data.model.movie.MovieEntity
import banquemisr.challenge05.data.model.movie.MovieEntityType
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var moviesDao: MoviesDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        moviesDao = database.moviesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun testAddMovies() = runBlocking {
        val movies = listOf(
            MovieEntity(
                id = 1,
                type = MovieEntityType.Discover,
                posterPath = "path1",
                title = "Title1",
                originalTitle = "OriginalTitle1",
                releaseDate = "2020-01-01",
                language = "en"
            ),
            MovieEntity(
                id = 2,
                type = MovieEntityType.Upcoming,
                posterPath = "path2",
                title = "Title2",
                originalTitle = "OriginalTitle2",
                releaseDate = "2020-02-01",
                language = "en"
            )
        )

        moviesDao.addMovies(movies)

        val allMovies = moviesDao.getAllMovies(MovieEntityType.Discover, "en").load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page

        assertEquals(1, allMovies.data.size)
        assertEquals("Title1", allMovies.data[0].title)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllMovies() = runBlocking {
        val movies = listOf(
            MovieEntity(
                id = 1,
                type = MovieEntityType.Discover,
                posterPath = "path1",
                title = "Title1",
                originalTitle = "OriginalTitle1",
                releaseDate = "2020-01-01",
                language = "en"
            ),
            MovieEntity(
                id = 2,
                type = MovieEntityType.Discover,
                posterPath = "path2",
                title = "Title2",
                originalTitle = "OriginalTitle2",
                releaseDate = "2020-02-01",
                language = "en"
            ),
            MovieEntity(
                id = 3,
                type = MovieEntityType.Discover,
                posterPath = "path3",
                title = "Title3",
                originalTitle = "OriginalTitle3",
                releaseDate = "2020-03-01",
                language = "fr"
            )
        )

        moviesDao.addMovies(movies)

        val allMoviesEn = moviesDao.getAllMovies(MovieEntityType.Discover, "en").load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page

        assertEquals(2, allMoviesEn.data.size)
        assertEquals("Title1", allMoviesEn.data[0].title)
        assertEquals("Title2", allMoviesEn.data[1].title)

        val allMoviesFr = moviesDao.getAllMovies(MovieEntityType.Discover, "fr").load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        ) as PagingSource.LoadResult.Page

        assertEquals(1, allMoviesFr.data.size)
        assertEquals("Title3", allMoviesFr.data[0].title)
    }
}
