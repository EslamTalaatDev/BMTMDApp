package banquemisr.challenge05.data.repository.config

import banquemisr.challenge05.data.model.DeviceLanguage
import banquemisr.challenge05.data.model.Genre
import banquemisr.challenge05.data.model.ProviderSource
import banquemisr.challenge05.utils.ImageUrlParser
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    fun isInitialized(): Flow<Boolean>

    fun updateLocale()

    fun getSpeechToTextAvailable(): Flow<Boolean>

    fun getCameraAvailable(): Flow<Boolean>

    fun getDeviceLanguage(): Flow<DeviceLanguage>

    fun getImageUrlParser(): Flow<ImageUrlParser?>

    fun getMoviesGenres(): Flow<List<Genre>>

    fun getTvShowGenres(): Flow<List<Genre>>

    fun getAllMoviesWatchProviders(): Flow<List<ProviderSource>>

    fun getAllTvShowWatchProviders(): Flow<List<ProviderSource>>
}