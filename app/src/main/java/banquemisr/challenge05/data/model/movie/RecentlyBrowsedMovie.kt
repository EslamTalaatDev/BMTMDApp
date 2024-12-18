package banquemisr.challenge05.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import banquemisr.challenge05.data.model.Presentable
import java.util.*

@Entity
data class RecentlyBrowsedMovie(
    @PrimaryKey
    override val id: Int,
    @ColumnInfo(name = "poster_path")
    override val posterPath: String?,
    override val title: String,
    @ColumnInfo(name = "added_date")
    val addedDate: Date
) : Presentable {
    override val releaseDate: String?
        get() = TODO("Not yet implemented")
}
