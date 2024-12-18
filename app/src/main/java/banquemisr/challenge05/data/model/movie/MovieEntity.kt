package banquemisr.challenge05.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import banquemisr.challenge05.data.model.Presentable

@Entity(indices = [Index(value = ["type", "language"])])
data class MovieEntity(
    override val id: Int,
    val type: MovieEntityType,
    @ColumnInfo(name = "poster_path")
    override val posterPath: String?,
    override val title: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "release_date")
    override val releaseDate: String,
    val language: String
) : Presentable {
    @PrimaryKey(autoGenerate = true)
    var entityId: Int = 0
}

enum class MovieEntityType {
    Discover, Upcoming, Popular
}
