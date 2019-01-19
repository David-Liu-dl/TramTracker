package au.com.realestate.remote.auth.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DeviceTokenResponse(
    @Json(name = "__type")
    val type: String,
    @Json(name = "DeviceToken")
    val deviceToken: String
)