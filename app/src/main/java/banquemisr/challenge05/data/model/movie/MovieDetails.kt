package banquemisr.challenge05.data.model.movie

import banquemisr.challenge05.data.model.*
import banquemisr.challenge05.data.model.Collection
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class MovieDetails(
    override val id: Int,
    override val adult: Boolean,
    @Json(name = "backdrop_path")
    override val backdropPath: String?,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String?,
    @Json(name = "imdb_id")
    val imdbId: String?,
    @Json(name = "belongs_to_collection")
    val collection: Collection?,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    override val overview: String,
    val popularity: Float,
    @Json(name = "poster_path")
    override val posterPath: String?,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>,
    @Json(name = "release_date")
    override val releaseDate: String?,
    val revenue: Long,
    val runtime: Int?,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val tagline: String?,
    override val title: String,
    val video: Boolean,
    @Json(name = "vote_average")
    override val voteAverage: Float,
    @Json(name = "vote_count")
    override val voteCount: Int
) : DetailPresentable
