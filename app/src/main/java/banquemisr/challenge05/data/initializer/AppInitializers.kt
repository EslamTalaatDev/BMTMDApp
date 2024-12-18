package banquemisr.challenge05.data.initializer

import android.app.Application
import banquemisr.challenge05.data.initializer.AppInitializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInitializers @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards AppInitializer>
) {
    fun init(application: Application) {
        initializers.forEach { initializer ->
            initializer.init(application)
        }
    }
}