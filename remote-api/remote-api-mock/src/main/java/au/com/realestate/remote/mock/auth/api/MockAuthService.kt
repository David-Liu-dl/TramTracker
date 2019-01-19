package au.com.realestate.remote.mock.auth.api

import au.com.realestate.remote.HttpResponse
import au.com.realestate.remote.auth.api.AuthService
import au.com.realestate.remote.auth.dto.DeviceTokenResponse
import au.com.realestate.remote.mock.auth.FakeDeviceTokenResponse
import io.reactivex.Single
import retrofit2.mock.BehaviorDelegate


class MockAuthService(private val behaviorDelegate: BehaviorDelegate<AuthService>) : AuthService {
    override fun getDeviceToken(aid: String, devInfo: String): Single<HttpResponse<List<DeviceTokenResponse>>> {
        return behaviorDelegate.returningResponse(FakeDeviceTokenResponse.httpDeviceTokenResponse)
            .getDeviceToken(aid, devInfo)
    }
}