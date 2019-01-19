package au.com.realestate.remote.mock.auth

import au.com.realestate.remote.HttpResponse
import au.com.realestate.remote.auth.dto.DeviceTokenResponse

object FakeDeviceTokenResponse {
    private val deviceTokenResponse = DeviceTokenResponse(
        type = "AddDeviceTokenInfo",
        deviceToken = "99f7accc-e1a2-445d-9437-61b9045fd093"
    )

    val httpDeviceTokenResponse
        get() = HttpResponse(
            errorMessage = null,
            hasError = false,
            hasResponse = true,
            responseObject = listOf(deviceTokenResponse),
            timeRequested = "/Date(1547858980631+1100)/",
            timeResponded = "/Date(1547858980643+1100)/",
            webMethodCalled = "GetDeviceToken"
        )
}