package banquemisr.challenge05.ui.screens.browse.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import banquemisr.challenge05.data.model.DeviceLanguage
import banquemisr.challenge05.domain.usecase.GetDeviceLanguageUseCaseImpl
import banquemisr.challenge05.domain.usecase.movie.ClearRecentlyBrowsedMoviesUseCaseImpl
import banquemisr.challenge05.domain.usecase.movie.GetMoviesOfTypeUseCaseImpl
import banquemisr.challenge05.ui.screens.destinations.BrowseMoviesScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class BrowseMoviesViewModel @Inject constructor(
    private val getDeviceLanguageUseCase: GetDeviceLanguageUseCaseImpl,
    private val getMoviesOfTypeUseCase: GetMoviesOfTypeUseCaseImpl,
    private val getClearRecentlyBrowsedMoviesUseCase: ClearRecentlyBrowsedMoviesUseCaseImpl,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val deviceLanguage: Flow<DeviceLanguage> = getDeviceLanguageUseCase()
    private val navArgs: BrowseMoviesScreenArgs =
        BrowseMoviesScreenDestination.argsFrom(savedStateHandle)


    val uiState: StateFlow<BrowseMoviesScreenUIState> = combine(
        deviceLanguage
    ) { deviceLanguage ->
        val movies = getMoviesOfTypeUseCase(
            type = navArgs.movieType,
            deviceLanguage = DeviceLanguage.default
        ).cachedIn(viewModelScope)

        BrowseMoviesScreenUIState(
            selectedMovieType = navArgs.movieType,
            movies = movies,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        BrowseMoviesScreenUIState.getDefault(navArgs.movieType)
    )

    fun onClearClicked() = getClearRecentlyBrowsedMoviesUseCase()
}