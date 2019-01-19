package au.com.realestate.remote.tram.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RouteResponse(
    @Json(name = "__type")
    val type: String,
    @Json(name = "HasSpecialEvent")
    val hasSpecialEvent: Boolean,
    @Json(name = "RouteNo")
    val routeNo: Int,
    @Json(name = "PredictedArrivalDateTime")
    val predictedArrivalDateTime: String,
    @Json(name = "SpecialEventMessage")
    val specialEventMessage: String? = null
)
