package banquemisr.challenge05.data.remote.api.others

import banquemisr.challenge05.data.model.*
import retrofit2.Call

interface TmdbOthersApiHelper {
    fun getConfig(): Call<Config>



    fun getCollection(
        collectionId: Int,
        isoCode: String = DeviceLanguage.default.languageCode
    ): Call<CollectionResponse>

}