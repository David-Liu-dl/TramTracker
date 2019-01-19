package au.com.realestate.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class HttpResponse<T>(
    @Json(name = "errorMessage")
    val errorMessage: String? = null,
    @Json(name = "hasError")
    val hasError: Boolean,
    @Json(name = "hasResponse")
    val hasResponse: Boolean? = null,
    @Json(name = "responseObject")
    val responseObject: T,
    @Json(name = "timeRequested")
    val timeRequested: String,
    @Json(name = "timeResponded")
    val timeResponded: String,
    @Json(name = "webMethodCalled")
    val webMethodCalled: String
)