package au.com.realestate.remote.mock.tram

import au.com.realestate.remote.HttpResponse
import au.com.realestate.remote.auth.dto.DeviceTokenResponse
import au.com.realestate.remote.tram.dto.RouteResponse

object FakeGetNextPredictedRoutesCollectionResponse {
    private val routeResponse = RouteResponse(
        type = "NextPredictedRoutesCollectionInfo",
        hasSpecialEvent = true,
        routeNo = 78,
        destination = "North Richmond",
        predictedArrivalDateTime = "/Date(1547859660000+1100)/",
        specialEventMessage = "Public event Saturday 26 January: Service changes will affect some City trams. Info: yarratrams.com.au/servicechanges"
    )

    val httpRouteResponse
        get() = HttpResponse(
            errorMessage = null,
            hasError = false,
            hasResponse = true,
            responseObject = listOf(routeResponse, routeResponse),
            timeRequested = "/Date(1547858980631+1100)/",
            timeResponded = "/Date(1547858980643+1100)/",
            webMethodCalled = "GetDeviceToken"
        )
}