package banquemisr.challenge05.data.repository.config

import banquemisr.challenge05.data.model.DeviceLanguage
import banquemisr.challenge05.data.model.Genre
import banquemisr.challenge05.data.model.ProviderSource
import banquemisr.challenge05.data.paging.ConfigDataSource
import banquemisr.challenge05.utils.ImageUrlParser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val configDataSource: ConfigDataSource
) : ConfigRepository {
    override fun isInitialized(): Flow<Boolean> {
        return configDataSource.isInitialized
    }

    override fun updateLocale() {
        return configDataSource.updateLocale()
    }

    override fun getSpeechToTextAvailable(): Flow<Boolean> {
        return configDataSource.speechToTextAvailable
    }

    override fun getCameraAvailable(): Flow<Boolean> {
        return configDataSource.hasCamera
    }

    override fun getDeviceLanguage(): Flow<DeviceLanguage> {
        return configDataSource.deviceLanguage
    }

    override fun getImageUrlParser(): Flow<ImageUrlParser?> {
        return configDataSource.imageUrlParser
    }

    override fun getMoviesGenres(): Flow<List<Genre>> {
        return configDataSource.movieGenres
    }

    override fun getTvShowGenres(): Flow<List<Genre>> {
        return configDataSource.tvShowGenres
    }

    override fun getAllMoviesWatchProviders(): Flow<List<ProviderSource>> {
        return configDataSource.movieWatchProviders
    }

    override fun getAllTvShowWatchProviders(): Flow<List<ProviderSource>> {
        return configDataSource.tvShowWatchProviders
    }
}