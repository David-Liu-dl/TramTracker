package au.com.realestate.remote.tram.api

import au.com.realestate.remote.HttpResponse
import au.com.realestate.remote.tram.dto.RouteResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TramService {

    @GET("/TramTracker/RestService//GetNextPredictedRoutesCollection/{stopId}/{directionId}/{hideResponseObject}")
    fun getNextPredictedRoutesCollection(
        @Path("stopId") stopId: Int,
        @Path("directionId") tramId: Int,
        @Path("hideResponseObject") hideResponseObject: Boolean,
        @Query("aid") aid: String,
        @Query("cid") cid: String
    ): Single<HttpResponse<List<RouteResponse>>>

}