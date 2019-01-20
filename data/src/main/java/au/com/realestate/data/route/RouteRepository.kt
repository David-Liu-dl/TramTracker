package au.com.realestate.data.route

import au.com.realestate.domain.route.model.Direction
import au.com.realestate.domain.route.model.Route
import au.com.realestate.domain.route.repository.RouteRepository
import au.com.realestate.remote.HttpExceptionDefiner
import au.com.realestate.remote.tram.api.TramService
import io.reactivex.Single

class RouteRepositoryImpl(
    private val tramService: TramService,
    private val httpErrorDefiner: HttpExceptionDefiner
) : RouteRepository {
    private val tramId = 78
    private val hideResponseObject = false
    private val aid = "TTIOSJSON"
    private val cid = "2"

    override fun getNorthComingTrams(): Single<List<Route>> {
        return tramService.getNextPredictedRoutesCollection(
            Direction.North.getId(), tramId, hideResponseObject, aid, cid
        )
            .map {
                httpErrorDefiner.defineHttpException(it)
            }
            .flatMap {
                Single.just(it.responseObject!!.map { routeResponse ->
                    Route(
                        routeResponse.hasSpecialEvent,
                        routeResponse.routeNo,
                        routeResponse.destination,
                        routeResponse.predictedArrivalDateTime,
                        routeResponse.specialEventMessage
                    )
                })
            }
    }

    override fun getSouthComingTrams(): Single<List<Route>> {
        return tramService.getNextPredictedRoutesCollection(
            Direction.South.getId(), tramId, hideResponseObject, aid, cid
        )
            .map {
                httpErrorDefiner.defineHttpException(it)
            }
            .flatMap {
                Single.just(it.responseObject!!.map { routeResponse ->
                    Route(
                        routeResponse.hasSpecialEvent,
                        routeResponse.routeNo,
                        routeResponse.destination,
                        routeResponse.predictedArrivalDateTime,
                        routeResponse.specialEventMessage
                    )
                })
            }
    }
}