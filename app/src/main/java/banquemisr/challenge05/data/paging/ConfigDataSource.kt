package banquemisr.challenge05.data.paging

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import banquemisr.challenge05.data.model.Config
import banquemisr.challenge05.data.model.DeviceLanguage
import banquemisr.challenge05.data.model.Genre
import banquemisr.challenge05.data.model.ProviderSource
import banquemisr.challenge05.data.remote.api.movie.TmdbMoviesApiHelper
import banquemisr.challenge05.data.remote.api.onException
import banquemisr.challenge05.data.remote.api.onSuccess
import banquemisr.challenge05.data.remote.api.others.TmdbOthersApiHelper
import banquemisr.challenge05.data.remote.api.request
import banquemisr.challenge05.utils.ImageUrlParser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ConfigDataSource @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val externalScope: CoroutineScope,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val apiMovieHelper: TmdbMoviesApiHelper,
) {
    private val _config: MutableStateFlow<Config?> = MutableStateFlow(null)

    @SuppressLint("QueryPermissionsNeeded")
    val speechToTextAvailable: Flow<Boolean> = flow {
        val packageManager = context.packageManager
        val activities: List<*> = packageManager.queryIntentActivities(
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),
            0
        )

        emit(activities.isNotEmpty())
    }.flowOn(defaultDispatcher)

    val hasCamera: Flow<Boolean> = flow {
        val packageManager = context.packageManager
        val hasCamera = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
        emit(hasCamera)
    }

    private val _deviceLanguage: MutableStateFlow<DeviceLanguage> =
        MutableStateFlow(getCurrentDeviceLanguage())
    val deviceLanguage: StateFlow<DeviceLanguage> = _deviceLanguage.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val imageUrlParser: Flow<ImageUrlParser?> = _config.mapLatest { config ->
        if (config != null) {
            ImageUrlParser(config.imagesConfig)
        } else null
    }.flowOn(defaultDispatcher)

    private val _movieGenres: MutableStateFlow<List<Genre>> = MutableStateFlow(emptyList())
    val movieGenres: StateFlow<List<Genre>> = _movieGenres.asStateFlow()

    private val _tvShowGenres: MutableStateFlow<List<Genre>> = MutableStateFlow(emptyList())
    val tvShowGenres: StateFlow<List<Genre>> = _tvShowGenres.asStateFlow()

    private val _movieWatchProviders: MutableStateFlow<List<ProviderSource>> = MutableStateFlow(
        emptyList()
    )
    val movieWatchProviders: StateFlow<List<ProviderSource>> = _movieWatchProviders.asStateFlow()

    private val _tvShowWatchProviders: MutableStateFlow<List<ProviderSource>> = MutableStateFlow(
        emptyList()
    )
    val tvShowWatchProviders: StateFlow<List<ProviderSource>> = _tvShowWatchProviders.asStateFlow()

    val isInitialized: StateFlow<Boolean> = combine(
        _config, _movieGenres, _tvShowGenres, _movieWatchProviders, _tvShowWatchProviders
    ) { imageUrlParser, movieGenres, tvShowGenres, movieWatchProviders, tvSeriesWatchProviders ->
        val imageUrlParserInit = imageUrlParser != null
        val movieGenresInit = movieGenres.isNotEmpty()
        val tvSeriesGenresInit = tvShowGenres.isNotEmpty()
        val movieWatchProvidersInit = movieWatchProviders.isNotEmpty()
        val tvSeriesWatchProvidersInit = tvSeriesWatchProviders.isNotEmpty()

        listOf(
            imageUrlParserInit,
            movieGenresInit,
            tvSeriesGenresInit,
            movieWatchProvidersInit,
            tvSeriesWatchProvidersInit
        ).all { init -> init }
    }.stateIn(externalScope, SharingStarted.WhileSubscribed(10), false)

    fun init() {
        apiMovieHelper.getConfig().request { response ->
            response.onSuccess {
                externalScope.launch(defaultDispatcher) {
                    val config = data
                    _config.emit(config)
                }
            }.onException {
            }
        }




        externalScope.launch(defaultDispatcher) {
            deviceLanguage.collectLatest { deviceLanguage ->

            }
        }
    }

    fun updateLocale() {
        externalScope.launch {
            val deviceLanguage = getCurrentDeviceLanguage()
            _deviceLanguage.emit(deviceLanguage)
        }
    }

    private fun getCurrentDeviceLanguage(): DeviceLanguage {
        val locale = Locale.getDefault()

        val languageCode = locale.toLanguageTag().ifEmpty { DeviceLanguage.default.languageCode }
        val region = locale.country.ifEmpty { DeviceLanguage.default.region }

        return DeviceLanguage(
            languageCode = languageCode,
            region = region
        )
    }

}