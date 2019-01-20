package au.com.realestate.remote.auth.api

import au.com.realestate.remote.HttpResponse
import au.com.realestate.remote.auth.dto.DeviceTokenResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthService {

    @GET("/TramTracker/RestService/GetDeviceToken")
    fun getDeviceToken(
        @Query("aid") aid: String,
        @Query("devInfo") devInfo: String
    ): Single<HttpResponse<List<DeviceTokenResponse>>>

}
