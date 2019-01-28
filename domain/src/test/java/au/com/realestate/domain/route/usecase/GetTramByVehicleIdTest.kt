package au.com.realestate.domain.route.usecase

import au.com.realestate.domain.route.model.Route
import au.com.realestate.domain.route.repository.RouteRepository
import au.com.realestate.infra.testing.TestSchedulerProvider
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class GetTramByVehicleIdTest {

    private val dummyExistVehicleId = 200

    private val dummyNonExistVehicleId = -1

    private val dummyRoute1 = Route(
        hasSpecialEvent = true,
        routeNo = 78,
        destination = "North Richmond",
        predictedArrivalDateTime = "/Date(1547859660000+1100)/",
        specialEventMessage = "Public event Saturday 26 January: Service changes will affect some City trams. Info: yarratrams.com.au/servicechanges",
        vehicleId = dummyExistVehicleId
    )

    private val dummyRoute2 = Route(
        hasSpecialEvent = true,
        routeNo = 78,
        destination = "North Richmond",
        predictedArrivalDateTime = "/Date(1547859660000+1100)/",
        specialEventMessage = "Public event Saturday 26 January: Service changes will affect some City trams. Info: yarratrams.com.au/servicechanges",
        vehicleId = 201
    )

    private val dummyRoutes = listOf(
        dummyRoute1,
        dummyRoute2
    )

    private val routeRepository = mockk<RouteRepository> {
        every { getComingTrams(any()) } returns Single.just(dummyRoutes)
    }

    @Test
    fun `should return Route with selected VehicleId when exist`() {
        val getTramByVehicleId = GetTramByVehicleId(routeRepository, TestSchedulerProvider())

        val testObserver = getTramByVehicleId.get(GetTramByVehicleId.Params(dummyExistVehicleId)).test()

        testObserver.assertValue(dummyRoute1)
    }

    @Test
    fun `should throw RuntimeException when selected vehicleId is not exist`() {
        val getTramByVehicleId = GetTramByVehicleId(routeRepository, TestSchedulerProvider())

        val testObserver = getTramByVehicleId.get(GetTramByVehicleId.Params(dummyNonExistVehicleId)).test()

        testObserver.assertErrorMessage("No Route found by vehicleId $dummyNonExistVehicleId")
    }
}