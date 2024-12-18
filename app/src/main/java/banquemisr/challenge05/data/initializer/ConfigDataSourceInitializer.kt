package banquemisr.challenge05.data.initializer

import android.app.Application
import banquemisr.challenge05.data.initializer.AppInitializer
import banquemisr.challenge05.data.paging.ConfigDataSource
import javax.inject.Inject

class ConfigDataSourceInitializer @Inject constructor(
    private val configDataSource: ConfigDataSource
    ) : AppInitializer {
    override fun init(application: Application) {
        configDataSource.init()
    }
}