package banquemisr.challenge05.ui.components.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import banquemisr.challenge05.data.model.DetailPresentableItemState
import banquemisr.challenge05.ui.theme.Size
import banquemisr.challenge05.ui.theme.sizes

@Composable
fun MovplayDetailPresentableItem(
    presentableState: DetailPresentableItemState,
    modifier: Modifier = Modifier,
    size: Size = MaterialTheme.sizes.presentableItemSmall,
    selected: Boolean = false,
    showTitle: Boolean = true,
    showAdult: Boolean = false,
    transformations: GraphicsLayerScope.() -> Unit = {},
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
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
            is DetailPresentableItemState.Loading -> {
                MovplayLoadingPresentableItem(
                    modifier = Modifier.fillMaxSize()
                )
            }
            is DetailPresentableItemState.Error -> {
                MovplayErrorPresentableItem(
                    modifier = Modifier.fillMaxSize()
                )
            }
            is DetailPresentableItemState.Result -> {
                MovplayResultDetailPresentableItem(
                    modifier = Modifier.fillMaxSize(),
                    presentable = presentableState.presentable,
                    showTitle = showTitle,
                    showAdult = showAdult,
                    onClick = onClick
                )
            }
        }
    }
}