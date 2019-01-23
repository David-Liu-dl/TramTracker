package au.com.realestate.domain.route.usecase

import au.com.realestate.domain.route.model.Direction
import au.com.realestate.domain.route.model.Route
import au.com.realestate.domain.route.repository.RouteRepository
import au.com.realestate.infra.SchedulerProvider
import au.com.realestate.infra.domain.SingleUseCase
import au.com.realestate.infra.domain.UseCaseParams
import io.reactivex.Single

class GetComingTrams(
    private val repository: RouteRepository,
    schedulerProvider: SchedulerProvider
) : SingleUseCase<GetComingTrams.Params, List<Route>>(schedulerProvider = schedulerProvider) {
    override fun createUseCase(): Single<List<Route>> {
        return repository.getComingTrams(params.direction)
    }

    class Params(var direction: Direction): UseCaseParams
}