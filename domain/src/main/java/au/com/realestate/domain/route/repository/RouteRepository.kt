package au.com.realestate.domain.route.repository

import au.com.realestate.domain.route.model.Route
import io.reactivex.Single

interface RouteRepository{
    fun getNorthComingTrams(): Single<List<Route>>
    fun getSouthComingTrams(): Single<List<Route>>
}
