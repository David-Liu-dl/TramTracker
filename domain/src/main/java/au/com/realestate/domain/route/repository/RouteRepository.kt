package au.com.realestate.domain.route.repository

import au.com.realestate.domain.route.model.Direction
import au.com.realestate.domain.route.model.Route
import io.reactivex.Single

interface RouteRepository{
    fun getComingTrams(direction: Direction): Single<List<Route>>
}
