package banquemisr.challenge05.domain.usecase.movie

import banquemisr.challenge05.data.model.movie.MovieDetails
import banquemisr.challenge05.data.repository.browsed.RecentlyBrowsedRepository
import javax.inject.Inject

class AddRecentlyBrowsedMovieUseCaseImpl @Inject constructor(
    private val recentlyBrowsedRepository: RecentlyBrowsedRepository
) {
    operator fun invoke(details: MovieDetails) {
        return recentlyBrowsedRepository.addRecentlyBrowsedMovie(details)
    }
}
