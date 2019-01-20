package au.com.realestate.remote.mock.tram.api

import au.com.realestate.remote.HttpResponse
import au.com.realestate.remote.mock.tram.FakeGetNextPredictedRoutesCollectionResponse
import au.com.realestate.remote.tram.api.TramService
import au.com.realestate.remote.tram.dto.RouteResponse
import io.reactivex.Single
import retrofit2.mock.BehaviorDelegate

class MockTramService(private val behaviorDelegate: BehaviorDelegate<TramService>) : TramService {
    override fun getNextPredictedRoutesCollection(
        stopId: Int,
        tramId: Int,
        hideResponseObject: Boolean,
        aid: String,
        cid: String,
        tkn: String
    ): Single<HttpResponse<List<RouteResponse>>> {
        return behaviorDelegate.returningResponse(FakeGetNextPredictedRoutesCollectionResponse.httpRouteResponse)
            .getNextPredictedRoutesCollection(
                stopId, tramId, hideResponseObject, aid, cid, tkn
            )
    }
}