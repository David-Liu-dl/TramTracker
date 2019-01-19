package au.com.realestate.domain.tram.model

data class Route(
    val hasSpecialEvent: Boolean,
    val routeNo: Int,
    val predictedArrivalDateTime: String,
    val specialEventMessage: String? = null
)