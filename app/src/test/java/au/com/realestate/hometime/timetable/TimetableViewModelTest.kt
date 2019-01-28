package au.com.realestate.hometime.timetable

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import au.com.realestate.hometime.timetable.routedetails.RouteDetailFragment
import io.mockk.mockk
import io.mockk.verifyAll
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test

class TimetableViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val stateObserver = mockk<Observer<Navigation>>(relaxed = true)

    @Test
    fun `should has initial value for initialNavigation`() {
        val initialFragmentTag = "LatestComingTramFragment"
        val initialNavigation = Navigation(initialFragmentTag, EmptyParams())
        val viewModel = TimetableViewModel(initialNavigation)

        viewModel.fragmentNavigator.value.shouldEqual(initialNavigation)
    }

    @Test
    fun `should has Navigation with RouteDetailFragmentParam and VehicleId when NavigateToRouteDetailFragmentByTag`() {
        val initialFragmentTag = "LatestComingTramFragment"
        val initialNavigation = Navigation(initialFragmentTag, EmptyParams())
        val viewModel = TimetableViewModel(initialNavigation)

        viewModel.fragmentNavigator.observeForever(stateObserver)

        val vehicleId = 200
        val routeDetailFragmentParam = RouteDetailFragmentParam(vehicleId)
        val routeDetailNavigation = Navigation(RouteDetailFragment.TAG, routeDetailFragmentParam)
        viewModel.navigateToRouteDetailFragment(routeDetailFragmentParam)

        verifyAll {
            stateObserver.onChanged(
                initialNavigation
            )

            stateObserver.onChanged(
                routeDetailNavigation
            )
        }
    }

}