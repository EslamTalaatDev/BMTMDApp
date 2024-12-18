package banquemisr.challenge05.data.local.db.movie

import androidx.paging.PagingSource
import androidx.room.*
import banquemisr.challenge05.data.model.movie.MovieEntity
import banquemisr.challenge05.data.model.movie.MovieEntityType
import banquemisr.challenge05.utils.MovieEntityTypeConverters

@TypeConverters(MovieEntityTypeConverters::class)
@Dao
interface MoviesDao {
    @Query("SELECT * FROM MovieEntity WHERE type=:type AND language=:language")
    fun getAllMovies(type: MovieEntityType, language: String): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM MovieEntity WHERE type=:type AND language=:language")
    suspend fun deleteMoviesOfType(type: MovieEntityType, language: String)
}