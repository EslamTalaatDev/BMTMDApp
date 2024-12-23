package banquemisr.challenge05

import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.data.model.SnackBarEvent
import banquemisr.challenge05.data.repository.config.ConfigRepository
import banquemisr.challenge05.utils.ImageUrlParser
import banquemisr.challenge05.utils.NetworkStatus
import banquemisr.challenge05.utils.NetworkStatusTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkStatusTracker: NetworkStatusTracker,
    private val configRepository: ConfigRepository
) : BaseViewModel() {
    private val connectionStatus = networkStatusTracker.connectionStatus

    @OptIn(ExperimentalCoroutinesApi::class)
    val networkSnackBarEvent: StateFlow<SnackBarEvent?> = connectionStatus.mapLatest { status ->
        when (status) {
            NetworkStatus.Connected -> SnackBarEvent.NetworkConnected
            NetworkStatus.Disconnected -> SnackBarEvent.NetworkDisconnected
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val sameBottomBarRouteChannel: Channel<String> = Channel()
    val sameBottomBarRoute: Flow<String> =
        sameBottomBarRouteChannel.receiveAsFlow().flowOn(Dispatchers.Main.immediate)

    val imageUrlParser: StateFlow<ImageUrlParser?> = configRepository.getImageUrlParser()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun updateLocale() {
        configRepository.updateLocale()
    }

    fun onSameRouteSelected(route: String) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            sameBottomBarRouteChannel.send(route)
        }
    }
}