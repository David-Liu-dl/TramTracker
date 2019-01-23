package au.com.realestate.domain.route.usecase

import au.com.realestate.domain.route.model.Direction
import au.com.realestate.domain.route.model.Route
import au.com.realestate.domain.route.repository.RouteRepository
import au.com.realestate.infra.testing.TestSchedulerProvider
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class GetComingTramsTest {

    private val dummyDirection = Direction.North

    private val dummyRoutes = listOf(
        Route(
            hasSpecialEvent = true,
            routeNo = 78,
            destination = "North Richmond",
            predictedArrivalDateTime = "/Date(1547859660000+1100)/",
            specialEventMessage = "Public event Saturday 26 January: Service changes will affect some City trams. Info: yarratrams.com.au/servicechanges"
        ),
        Route(
            hasSpecialEvent = true,
            routeNo = 78,
            destination = "North Richmond",
            predictedArrivalDateTime = "/Date(1547859660000+1100)/",
            specialEventMessage = "Public event Saturday 26 January: Service changes will affect some City trams. Info: yarratrams.com.au/servicechanges"
        )
    )

    private val routeRepository = mockk<RouteRepository>(){
        every { getComingTrams(any()) } returns Single.just(dummyRoutes)
    }

    @Test
    fun `should get list of route with Single decoration`() {
        val getNorthComingTramsUseCase = GetComingTrams(
            routeRepository,
            TestSchedulerProvider()
        )
        val testObserver = getNorthComingTramsUseCase.get(GetComingTrams.Params(dummyDirection)).test()
        testObserver.assertValue(dummyRoutes)
    }
}