package au.com.realestate.data.route

import au.com.realestate.data.HttpExceptionDefiner
import au.com.realestate.domain.route.model.Route
import au.com.realestate.remote.HttpResponse
import au.com.realestate.remote.auth.api.AuthService
import au.com.realestate.remote.auth.dto.DeviceTokenResponse
import au.com.realestate.remote.tram.api.TramService
import au.com.realestate.remote.tram.dto.RouteResponse
import com.nhaarman.mockitokotlin2.any
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test

class RouteRepositoryTest {

    private val dummyErrorGetDeviceTokenResponse = HttpResponse<List<DeviceTokenResponse>>(
        errorMessage = null,
        hasError = true,
        hasResponse = false,
        responseObject = null,
        timeRequested = "/Date(1547858980631+1100)/",
        timeResponded = "/Date(1547858980643+1100)/",
        webMethodCalled = "GetDeviceToken"
    )

    private val dummyAuthTokenResponse = listOf(
        DeviceTokenResponse(
            type = "AddDeviceTokenInfo",
            deviceToken = "99f7accc-e1a2-445d-9437-61b9045fd093"
        )
    )

    private val dummyNormalGetDeviceTokenResponse = HttpResponse(
        errorMessage = null,
        hasError = false,
        hasResponse = true,
        responseObject = dummyAuthTokenResponse,
        timeRequested = "/Date(1547858980631+1100)/",
        timeResponded = "/Date(1547858980643+1100)/",
        webMethodCalled = "GetDeviceToken"
    )

    private val dummyRouteResponse = listOf(
        RouteResponse(
            type = "NextPredictedRoutesCollectionInfo",
            hasSpecialEvent = true,
            routeNo = 78,
            destination = "North Richmond",
            predictedArrivalDateTime = "/Date(1547859660000+1100)/",
            specialEventMessage = "Public event Saturday 26 January: Service changes will affect some City trams. Info: yarratrams.com.au/servicechanges"
        )
    )

    private val dummyRoute = listOf(
        Route(
            hasSpecialEvent = true,
            routeNo = 78,
            destination = "North Richmond",
            predictedArrivalDateTime = "/Date(1547859660000+1100)/",
            specialEventMessage = "Public event Saturday 26 January: Service changes will affect some City trams. Info: yarratrams.com.au/servicechanges"
        )
    )

    private val dummyErrorGetNextPredictedRoutesCollectionResponse: HttpResponse<List<RouteResponse>> = HttpResponse(
        errorMessage = null,
        hasError = true,
        hasResponse = false,
        responseObject = null,
        timeRequested = "/Date(1547858980631+1100)/",
        timeResponded = "/Date(1547858980643+1100)/",
        webMethodCalled = "GetDeviceToken"
    )

    private val dummyNormalGetNextPredictedRoutesCollectionResponse = HttpResponse(
        errorMessage = null,
        hasError = false,
        hasResponse = true,
        responseObject = dummyRouteResponse,
        timeRequested = "/Date(1547858980631+1100)/",
        timeResponded = "/Date(1547858980643+1100)/",
        webMethodCalled = "GetDeviceToken"
    )

    @Test
    fun `should return routes list when remote call has no error`() {
        val authService = mockk<AuthService> {
            every { getDeviceToken(any(), any()) } returns Single.just(dummyNormalGetDeviceTokenResponse)
        }
        val tramService = mockk<TramService> {
            every { getNextPredictedRoutesCollection(any(), any(), any(), any(), any(), any()) } returns Single.just(
                dummyNormalGetNextPredictedRoutesCollectionResponse
            )
        }
        val repositoryImpl = RouteRepositoryImpl(tramService, authService,
            HttpExceptionDefiner()
        )

        val testObserver = repositoryImpl.getNorthComingTrams().test()

        testObserver.assertValue(dummyRoute)
    }

    @Test
    fun `should throw Exception when GetNextPredictedRoutesCollectionResponse has error `() {
        val authService = mockk<AuthService> {
            every { getDeviceToken(any(), any()) } returns Single.just(dummyNormalGetDeviceTokenResponse)
        }
        val tramService = mockk<TramService> {
            every { getNextPredictedRoutesCollection(any(), any(), any(), any(), any(), any()) } returns Single.just(dummyErrorGetNextPredictedRoutesCollectionResponse)
        }
        val repositoryImpl = RouteRepositoryImpl(tramService, authService,
            HttpExceptionDefiner()
        )
        val testObserverNorth = repositoryImpl.getNorthComingTrams().test()
        val testObserverSouth = repositoryImpl.getSouthComingTrams().test()

        testObserverNorth.assertError(HttpExceptionDefiner.HttpException.TramTrackerException(any()))
        testObserverSouth.assertError(HttpExceptionDefiner.HttpException.TramTrackerException(any()))
    }

    @Test
    fun `should throw Exception when GetDeviceToken has error `() {
        val authService = mockk<AuthService> {
            every { getDeviceToken(any(), any()) } returns Single.just(dummyErrorGetDeviceTokenResponse)
        }
        val tramService = mockk<TramService> {
            every { getNextPredictedRoutesCollection(any(), any(), any(), any(), any(), any()) } returns Single.just(dummyErrorGetNextPredictedRoutesCollectionResponse)
        }
        val repositoryImpl = RouteRepositoryImpl(tramService, authService,
            HttpExceptionDefiner()
        )
        val testObserverNorth = repositoryImpl.getNorthComingTrams().test()
        val testObserverSouth = repositoryImpl.getSouthComingTrams().test()

        testObserverNorth.assertError(HttpExceptionDefiner.HttpException.TramTrackerException(any()))
        testObserverSouth.assertError(HttpExceptionDefiner.HttpException.TramTrackerException(any()))
    }

    @Test
    fun `should not trigger GetDeviceToken when cached AuthToken `() {
        val authService = mockk<AuthService> {
            every { getDeviceToken(any(), any()) } returns Single.just(dummyNormalGetDeviceTokenResponse)
        }
        val tramService = mockk<TramService> {
            every { getNextPredictedRoutesCollection(any(), any(), any(), any(), any(), any()) } returns Single.just(
                dummyNormalGetNextPredictedRoutesCollectionResponse
            )
        }
        val repositoryImpl = RouteRepositoryImpl(tramService, authService,
            HttpExceptionDefiner()
        )

        repositoryImpl.getNorthComingTrams().test()
        repositoryImpl.getSouthComingTrams().test()

        verify(exactly = 1) {
            authService.getDeviceToken(any(), any())
        }
    }
}