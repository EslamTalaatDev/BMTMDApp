package banquemisr.challenge05.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import banquemisr.challenge05.data.model.DetailPresentable

@Entity(indices = [Index(value = ["language"])])
data class MovieDetailEntity(
    override val id: Int,
    @ColumnInfo(name = "poster_path")
    override val posterPath: String?,
    override val title: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "release_date")
   override val releaseDate: String,
    override val adult: Boolean?,
    override val overview: String,
    @ColumnInfo(name = "backdrop_path")
    override val backdropPath: String?,
    override val voteAverage: Float,
    override val voteCount: Int,
    val language: String
) : DetailPresentable {
    @PrimaryKey(autoGenerate = true)
    var entityId: Int = 0

}