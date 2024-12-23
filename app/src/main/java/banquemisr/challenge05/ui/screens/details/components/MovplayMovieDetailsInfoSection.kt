package banquemisr.challenge05.ui.screens.details.components

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import banquemisr.challenge05.R
import banquemisr.challenge05.data.model.movie.MovieDetails
import banquemisr.challenge05.ui.components.sections.MovplayGenresSection
import banquemisr.challenge05.ui.components.texts.MovplayAdditionalInfoText
import banquemisr.challenge05.ui.components.texts.MovplayExpandableText
import banquemisr.challenge05.ui.theme.spacing
import banquemisr.challenge05.utils.formattedRuntime
import banquemisr.challenge05.utils.timeString
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MovplayMovieDetailsInfoSection(
    movieDetails: MovieDetails?,
    watchAtTime: Date?,
    modifier: Modifier = Modifier,
) {
    val otherOriginalTitle: Boolean by derivedStateOf {
        movieDetails?.run { originalTitle.isNotEmpty() && title != originalTitle } ?: false
    }
    val watchAtTimeString = watchAtTime?.let { time ->
        stringResource(R.string.movie_details_watch_at, time.timeString())
    }
    Crossfade(modifier = modifier, targetState = movieDetails) { details ->
        if (details != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = details.title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold
                        )
                        if (otherOriginalTitle) {
                            Text(text = details.originalTitle)
                        }
                        MovplayAdditionalInfoText(
                            modifier = Modifier.fillMaxWidth(),
                            infoTexts = details.run {
                                listOfNotNull(
                                    releaseDate,
                                    runtime?.formattedRuntime(),
                                    watchAtTimeString
                                )
                            }
                        )
                    }

                }
                if (details.genres.isNotEmpty()) {
                    MovplayGenresSection(genres = details.genres)
                }
                Column(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
                ) {
                    details.tagline?.let { tagline ->
                        if (tagline.isNotEmpty()) {
                            Text(
                                text = "\"$tagline\"",
                                fontStyle = FontStyle.Italic,
                                fontSize = 12.sp
                            )
                        }
                    }

                    details.overview.let { overview ->
                        if (overview.isNotBlank()) {
                            MovplayExpandableText(
                                modifier = Modifier.fillMaxWidth(),
                                text = overview
                            )
                        }
                    }
                }
            }
        }
    }
}