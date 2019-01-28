package au.com.realestate.domain.route.usecase

import au.com.realestate.domain.route.model.Direction
import au.com.realestate.domain.route.model.Route
import au.com.realestate.domain.route.repository.RouteRepository
import au.com.realestate.infra.SchedulerProvider
import au.com.realestate.infra.domain.SingleUseCase
import au.com.realestate.infra.domain.UseCaseParams
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import java.lang.RuntimeException

class GetTramByVehicleId(
    private val repository: RouteRepository,
    schedulerProvider: SchedulerProvider
) : SingleUseCase<GetTramByVehicleId.Params, Route>(schedulerProvider = schedulerProvider) {
    override fun createUseCase(): Single<Route> {
        return repository.getComingTrams(Direction.South)
            .zipWith(repository.getComingTrams(Direction.North))
            .map {
                listOf(it.first, it.second).flatten()
            }
            .map {
                it.find { route -> route.vehicleId == params.vehicleId } ?: let {
                    throw RuntimeException("No Route found by vehicleId ${params.vehicleId}")
                }
            }
    }

    class Params(var vehicleId: Int) : UseCaseParams
}