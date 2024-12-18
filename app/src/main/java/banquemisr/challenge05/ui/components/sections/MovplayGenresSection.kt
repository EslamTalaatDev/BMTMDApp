package banquemisr.challenge05.ui.components.sections

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import banquemisr.challenge05.data.model.Genre
import banquemisr.challenge05.ui.components.chips.MovplayGenreChip
import banquemisr.challenge05.ui.theme.spacing
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun MovplayGenresSection(
    genres: List<Genre>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        mainAxisSpacing = MaterialTheme.spacing.extraSmall,
        crossAxisSpacing = MaterialTheme.spacing.extraSmall
    ) {
        genres.map { genre ->
            MovplayGenreChip(text = genre.name)
        }
    }
}