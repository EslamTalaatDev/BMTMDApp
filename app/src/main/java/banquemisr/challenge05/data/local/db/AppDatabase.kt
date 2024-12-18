package banquemisr.challenge05.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import banquemisr.challenge05.data.local.db.movie.*
import banquemisr.challenge05.data.model.movie.*
import banquemisr.challenge05.utils.DateConverters

@Database(
    entities = [
        RecentlyBrowsedMovie::class,
        MovieEntity::class,
        MoviesRemoteKeys::class,
        MovieDetailEntity::class,
        MovieDetailsRemoteKey::class,
    ],
    version = 1
)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun recentlyBrowsedMovies(): RecentlyBrowsedMoviesDao
    abstract fun moviesRemoteKeysDao(): MoviesRemoteKeysDao
    abstract fun moviesDetailsDao(): MoviesDetailsDao
    abstract fun moviesDetailsRemoteKeys(): MoviesDetailsRemoteKeysDao

}