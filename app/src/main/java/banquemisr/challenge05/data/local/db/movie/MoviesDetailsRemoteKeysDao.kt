package banquemisr.challenge05.data.local.db.movie

import androidx.room.*
import banquemisr.challenge05.data.model.movie.MovieDetailsRemoteKey
import banquemisr.challenge05.utils.MovieEntityTypeConverters

@TypeConverters(MovieEntityTypeConverters::class)
@Dao
interface MoviesDetailsRemoteKeysDao {
    @Query("SELECT * FROM MovieDetailsRemoteKey WHERE language=:language LIMIT 1")
    suspend fun getRemoteKey(language: String): MovieDetailsRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: MovieDetailsRemoteKey)

    @Query("DELETE FROM MovieDetailsRemoteKey WHERE language=:language")
    suspend fun deleteKeys(language: String)
}