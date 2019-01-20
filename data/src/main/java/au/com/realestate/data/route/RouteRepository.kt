package au.com.realestate.data.route

import au.com.realestate.data.HttpExceptionDefiner
import au.com.realestate.domain.route.model.Direction
import au.com.realestate.domain.route.model.Route
import au.com.realestate.domain.route.repository.RouteRepository
import au.com.realestate.remote.auth.api.AuthService
import au.com.realestate.remote.tram.api.TramService
import io.reactivex.Maybe
import io.reactivex.Single

class RouteRepositoryImpl(
    private val tramService: TramService,
    private val authService: AuthService,
    private val httpErrorDefiner: HttpExceptionDefiner
) : RouteRepository {
    private var cachedAuthDeviceToken: Maybe<String> = Maybe.empty()

    private val tramId = 78
    private val hideResponseObject = false
    private val aid = "TTIOSJSON"
    private val cid = "2"
    private val devInfo = "HomeTimeAndroid"

    override fun getNorthComingTrams(): Single<List<Route>> {
        return getToken().flatMap { tkn ->
            tramService.getNextPredictedRoutesCollection(
                Direction.North.getId(), tramId, hideResponseObject, aid, cid, tkn
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

    override fun getSouthComingTrams(): Single<List<Route>> {
        return getToken().flatMap { tkn ->
            tramService.getNextPredictedRoutesCollection(
                Direction.South.getId(), tramId, hideResponseObject, aid, cid, tkn
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

    private fun getToken(): Single<String> {
        return this.cachedAuthDeviceToken.switchIfEmpty(Single.defer(this::fetchToken))
    }

    private fun fetchToken(): Single<String> {
        return authService.getDeviceToken(aid, devInfo)
            .map {
                httpErrorDefiner.defineHttpException(it)
            }
            .map {
                val authDeviceToken = it.responseObject!![0].deviceToken

                this.cachedAuthDeviceToken = Maybe.just(authDeviceToken)
                it.responseObject!![0].deviceToken
            }
    }
}