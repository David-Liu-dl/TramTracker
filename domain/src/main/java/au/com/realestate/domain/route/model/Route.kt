package au.com.realestate.domain.route.model

import java.util.*

data class Route(
    val hasSpecialEvent: Boolean,
    val routeNo: Int,
    val destination: String,
    val predictedArrivalDateTime: String,
    val specialEventMessage: String? = null
) {
    fun getFormattedArrivalDateTime(): Date {
        val startIndex = predictedArrivalDateTime.indexOf("(") + 1
        val endIndex = predictedArrivalDateTime.indexOf("+")
        val date = predictedArrivalDateTime.substring(startIndex, endIndex)

        val unixTime = java.lang.Long.parseLong(date)
        return Date(unixTime)
    }
}