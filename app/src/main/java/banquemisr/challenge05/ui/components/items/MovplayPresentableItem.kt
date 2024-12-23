package banquemisr.challenge05.ui.components.items

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import banquemisr.challenge05.data.model.PresentableItemState
import banquemisr.challenge05.ui.theme.Size
import banquemisr.challenge05.ui.theme.sizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovplayPresentableItem(
    presentableState: PresentableItemState,
    modifier: Modifier = Modifier,
    size: Size = MaterialTheme.sizes.presentableItemSmall,
    selected: Boolean = false,
    showTitle: Boolean = false,
    transformations: GraphicsLayerScope.() -> Unit = {},
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .width(size.width)
            .aspectRatio(size.ratio)
            .graphicsLayer {
                transformations()
            },
        shape = MaterialTheme.shapes.medium,
        border = if (selected) BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary
        ) else null
    ) {
        when (presentableState) {
            is PresentableItemState.Loading -> {
                MovplayLoadingPresentableItem(
                    modifier = Modifier.fillMaxSize()
                )
            }
            is PresentableItemState.Error -> {
                MovplayErrorPresentableItem(
                    modifier = Modifier.fillMaxSize()
                )
            }
            is PresentableItemState.Result -> {

                 MovplayResultPresentableItem(
                    modifier = Modifier.fillMaxSize(),
                    presentable = presentableState.presentable,
                    showTitle = showTitle,
                    onClick = onClick
                )
            }
        }
    }
}