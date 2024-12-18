package banquemisr.challenge05.di

import android.content.Context
import androidx.room.Room
import banquemisr.challenge05.data.local.db.AppDatabase
import banquemisr.challenge05.data.local.db.movie.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()




    @Provides
    fun provideRecentlyBrowsedMoviesDao(database: AppDatabase): RecentlyBrowsedMoviesDao =
        database.recentlyBrowsedMovies()



    @Provides
    fun provideMoviesDao(database: AppDatabase): MoviesDao = database.moviesDao()

    @Provides
    fun provideMovieRemoteKeysDao(database: AppDatabase): MoviesRemoteKeysDao =
        database.moviesRemoteKeysDao()


    @Provides
    fun provideMovieDetailsDao(database: AppDatabase): MoviesDetailsDao =
        database.moviesDetailsDao()

    @Provides
    fun provideMovieDetailsRemoteKeysDao(database: AppDatabase): MoviesRemoteKeysDao =
        database.moviesRemoteKeysDao()


}