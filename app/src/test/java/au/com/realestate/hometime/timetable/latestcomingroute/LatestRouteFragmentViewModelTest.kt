package au.com.realestate.hometime.timetable.latestcomingroute

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import au.com.realestate.domain.route.model.Direction
import au.com.realestate.domain.route.model.Route
import au.com.realestate.domain.route.usecase.GetComingTrams
import au.com.realestate.hometime.common.uimodels.RouteHeader
import au.com.realestate.infra.util.Clock
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.util.*

class LatestRouteFragmentViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getComingTrams = mockk<GetComingTrams>()
    private val clock = mockk<Clock>()

    private val dummyNorthRoutes = listOf(
        Route(
            hasSpecialEvent = true,
            routeNo = 78,
            destination = "North Richmond",
            predictedArrivalDateTime = "/Date(1547859660000+1100)/",
            specialEventMessage = "Public event Saturday 26 January: Service changes will affect some City trams. Info: yarratrams.com.au/servicechanges"
        )
    )

    private val dummySouthRoutes = listOf(
        Route(
            hasSpecialEvent = true,
            routeNo = 78,
            destination = "South Melbourne",
            predictedArrivalDateTime = "/Date(1547859660000+1100)/",
            specialEventMessage = "Public event Saturday 26 January: Service changes will affect some City trams. Info: yarratrams.com.au/servicechanges"
        )
    )

    private val dummyRefreshResult = mapOf(
        RouteHeader(Direction.South, routeId) to dummySouthRoutes,
        RouteHeader(Direction.North, routeId) to dummyNorthRoutes
    )

    @Test
    fun `should has correct initial states when created`(){
        val viewModel = LatestRouteFragmentViewModel(getComingTrams, clock)

        viewModel.tramsWithDirection.value.shouldEqual(emptyMap())
        viewModel.lastUpdatedDateTime.value.shouldEqual(null)
        viewModel.refreshing.value.shouldEqual(false)
        viewModel.shouldPopErrorSnackbar.value.shouldEqual(false)
    }

    @Test
    fun `should has correct states when refreshing`(){
        every { getComingTrams.get(any()) } returns Single.never()

        val viewModel = LatestRouteFragmentViewModel(getComingTrams, clock)

        viewModel.refreshingRoute()

        viewModel.tramsWithDirection.value.shouldEqual(emptyMap())
        viewModel.lastUpdatedDateTime.value.shouldEqual(null)
        viewModel.refreshing.value.shouldEqual(true)
        viewModel.shouldPopErrorSnackbar.value.shouldEqual(false)
    }

    @Test
    fun `should has correct states when refresh finished`(){
        val instantTime = Instant.now().toEpochMilli()

        every { getComingTrams.get(any()) }.returnsMany(Single.just(dummyNorthRoutes), Single.just(dummySouthRoutes))
        every { clock.currentTimeMillis } returns instantTime

        val viewModel = LatestRouteFragmentViewModel(getComingTrams, clock)

        viewModel.refreshingRoute()

        viewModel.tramsWithDirection.value.shouldEqual(dummyRefreshResult)
        viewModel.lastUpdatedDateTime.value.shouldEqual(Date(instantTime))
        viewModel.refreshing.value.shouldEqual(false)
        viewModel.shouldPopErrorSnackbar.value.shouldEqual(false)
    }

    @Test
    fun `should has correct states when error on refreshing`(){
        every { getComingTrams.get(any()) } returns Single.error(Exception())

        val viewModel = LatestRouteFragmentViewModel(getComingTrams, clock)

        viewModel.refreshingRoute()

        viewModel.tramsWithDirection.value.shouldEqual(emptyMap())
        viewModel.lastUpdatedDateTime.value.shouldEqual(null)
        viewModel.refreshing.value.shouldEqual(false)
        viewModel.shouldPopErrorSnackbar.value.shouldEqual(true)
    }
}