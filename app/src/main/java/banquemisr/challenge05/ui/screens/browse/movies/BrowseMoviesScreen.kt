package banquemisr.challenge05.ui.screens.browse.movies

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import banquemisr.challenge05.R
import banquemisr.challenge05.data.model.movie.MovieType
import banquemisr.challenge05.ui.components.others.MovplayBasicAppBar
import banquemisr.challenge05.ui.components.sections.MovplayPresentableGridSection
import banquemisr.challenge05.ui.screens.destinations.MovieDetailsScreenDestination
import banquemisr.challenge05.ui.screens.destinations.MovieScreenDestination
import banquemisr.challenge05.ui.theme.spacing
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class, ExperimentalLifecycleComposeApi::class)
@Destination(
    navArgsDelegate = BrowseMoviesScreenArgs::class,
    style = BrowseMoviesScreenTransitions::class
)
@Composable
fun AnimatedVisibilityScope.BrowseMoviesScreen(
    viewModel: BrowseMoviesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onBackClicked: () -> Unit = { navigator.navigateUp() }
    val onClearDialogConfirmClick: () -> Unit = viewModel::onClearClicked
    val onMovieClicked = { movieId: Int ->
        val destination = MovieDetailsScreenDestination(
            movieId = movieId,
            startRoute = MovieScreenDestination.route
        )
        navigator.navigate(destination)
    }
    BrowseMoviesScreenContent(
        uiState = uiState,
        onBackClicked = onBackClicked,
        onClearDialogConfirmClicked = onClearDialogConfirmClick,
        onMovieClicked = onMovieClicked
    )
}

@Composable
fun BrowseMoviesScreenContent(
    uiState: BrowseMoviesScreenUIState,
    onBackClicked: () -> Unit,
    onClearDialogConfirmClicked: () -> Unit,
    onMovieClicked: (movieId: Int) -> Unit
) {
    val movies = uiState.movies.collectAsLazyPagingItems()
    val appbarTitle = when (uiState.selectedMovieType) {
        MovieType.NowPlaying -> stringResource(R.string.now_playing)
        MovieType.Upcoming -> stringResource(R.string.upcoming)
        MovieType.Popular -> stringResource(R.string.popular)
    }

    var showClearDialog by remember {
        mutableStateOf(false)
    }


    val dismissDialog = {
        showClearDialog = false
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MovplayBasicAppBar(
            title = appbarTitle,
            action = {
                IconButton(
                    onClick = { onBackClicked() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },

        )
        MovplayPresentableGridSection(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(
                top = MaterialTheme.spacing.medium,
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small,
                bottom = MaterialTheme.spacing.large
            ),
            state = movies,
            onPresentableClick = onMovieClicked
        )
    }
}