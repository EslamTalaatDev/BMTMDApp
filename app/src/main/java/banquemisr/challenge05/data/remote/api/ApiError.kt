package banquemisr.challenge05.data.remote.api

data class ApiError(
    val errorCode: Int,
    val statusCode: Int?,
    val statusMessage: String?
)