package au.com.realestate.domain.route.usecase

import au.com.realestate.domain.route.model.Route
import au.com.realestate.domain.route.repository.RouteRepository
import au.com.realestate.infra.SchedulerProvider
import au.com.realestate.infra.domain.SingleUseCase
import io.reactivex.Single

class GetSouthComingTrams(
    private val repository: RouteRepository,
    schedulerProvider: SchedulerProvider
) : SingleUseCase<List<Route>>(schedulerProvider = schedulerProvider) {
    override fun createUseCase(): Single<List<Route>> {
        return repository.getSouthComingTrams()
    }
}