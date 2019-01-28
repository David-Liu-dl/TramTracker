package au.com.realestate.hometime.timetable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.lifecycle.Observer
import au.com.realestate.hometime.R
import au.com.realestate.hometime.timetable.latestcomingroute.LatestRouteFragment
import au.com.realestate.hometime.timetable.routedetails.RouteDetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.ClassCastException

class TimetableActivity : AppCompatActivity(), RouteNavigationHandler {

    private val viewModel by viewModel<TimetableViewModel>(
        parameters = { parametersOf(Navigation(LatestRouteFragment.TAG, EmptyParams())) }
    )

    private val latestComingTramFragmentProvider: (params: Params?) -> LatestRouteFragment = {
        LatestRouteFragment.newInstance()
    }

    private val routeDetailFragmentProvider: (params: Params?) -> RouteDetailFragment = { params ->
        (params as? RouteDetailFragmentParam)
            ?.let {
                RouteDetailFragment.newInstance(it.selectedVehicleId)
            }
            ?: let{
                throw ClassCastException("$params cannot be casted to RouteDetailFragmentParam")
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)
        viewModel.fragmentNavigator.observe(this, Observer { navigation: Navigation ->
            val tag = navigation.fragmentTag
            val params = navigation.params

            when (navigation.fragmentTag) {
                LatestRouteFragment.TAG -> {
                    showNewScreen(latestComingTramFragmentProvider, null, tag)
                }
                RouteDetailFragment.TAG -> {
                    showNewScreen(routeDetailFragmentProvider, params, tag)
                }
                else -> {
                    throw RuntimeException("NoSuchFragment $tag")
                }
            }
        })
    }

    override fun navigateToRouteDetailFragment(vehicleId: Int) {
        viewModel.navigateToRouteDetailFragmentByTag(vehicleId)
    }

    /**
     * Create a new fragment instance and add to the fragment stack
     * if fragment to create either doesn't exist in fragment stack or is not added to the fragment stack
     * We do this to make sure fragment's viewModel is retained on config change,
     * but destroyed as a result of state changes in the parent activity.
     */
    private fun showNewScreen(fragmentProvider: (params: Params?) -> Fragment, params: Params? = null, tag: String) {
        val existingFragment = supportFragmentManager.findFragmentByTag(tag)
        if (existingFragment == null || !existingFragment.isAdded) {
            supportFragmentManager.commitNow {
                replace(R.id.screen_container, fragmentProvider.invoke(params), tag)
            }
        }
    }
}
