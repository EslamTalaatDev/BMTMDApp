package banquemisr.challenge05.ui.components.sections

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import banquemisr.challenge05.data.model.Presentable
import banquemisr.challenge05.data.model.PresentableItemState
import banquemisr.challenge05.ui.components.button.MovplayScrollToTopButton
import banquemisr.challenge05.ui.components.items.MovplayPresentableItem
import banquemisr.challenge05.ui.components.others.gridVerticalScrollBar
import banquemisr.challenge05.ui.theme.spacing
import banquemisr.challenge05.utils.isScrollingTowardsStart
import banquemisr.challenge05.utils.items
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MovplayPresentableGridSection(
    state: LazyPagingItems<out Presentable>,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    showRefreshLoading: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(MaterialTheme.spacing.default),
    scrollToBeginningItemsStart: Int = 30,
    onPresentableClick: (Int) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val isScrollingLeft = gridState.isScrollingTowardsStart()
    val showScrollToBeginningButton by derivedStateOf {
        val visibleMaxItem = gridState.firstVisibleItemIndex > scrollToBeginningItemsStart

        visibleMaxItem && isScrollingLeft
    }
    val onScrollToStartClick: () -> Unit = {
        coroutineScope.launch {
            gridState.animateScrollToItem(0)
        }
    }

    Box(modifier = Modifier) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .gridVerticalScrollBar(gridState),
            state = gridState,
            contentPadding = contentPadding,
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
        ) {
            items(state) { presentable ->
                presentable?.let {
                    MovplayPresentableItem(
                        presentableState = PresentableItemState.Result(it),
                        onClick = { onPresentableClick(it.id) }

                    )
                }
            }
            state.apply {
                when {
                    loadState.refresh is LoadState.Loading && showRefreshLoading -> {
                        items(12) {
                            MovplayPresentableItem(
                                presentableState = PresentableItemState.Loading
                            )
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        items(3) {
                            MovplayPresentableItem(
                                presentableState = PresentableItemState.Loading
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = MaterialTheme.spacing.small, top = MaterialTheme.spacing.small),
            visible = showScrollToBeginningButton,
            enter = slideIn(
                animationSpec = tween(),
                initialOffset = { size -> IntOffset(size.width, 0) }),
            exit = fadeOut(animationSpec = spring()) + scaleOut(
                animationSpec = spring(),
                targetScale = 0.3f
            )
        ) {
            MovplayScrollToTopButton(
                onClick = onScrollToStartClick
            )
        }
    }
}