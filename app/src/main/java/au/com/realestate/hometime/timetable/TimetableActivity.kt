package au.com.realestate.hometime.timetable

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.lifecycle.Observer
import au.com.realestate.hometime.R
import au.com.realestate.hometime.timetable.latestcomingroute.LatestRouteFragment
import au.com.realestate.hometime.timetable.routedetails.RouteDetailFragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.ClassCastException

class TimetableActivity : AppCompatActivity(), RouteNavigationHandler {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    private lateinit var unbinder: Unbinder

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
            ?: let {
                throw ClassCastException("$params cannot be casted to RouteDetailFragmentParam")
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)
        unbinder = ButterKnife.bind(this)

        setSupportActionBar(toolbar)

        viewModel.fragmentNavigator.observe(this, Observer { navigation ->
            navigation?.let {
                val tag = navigation.fragmentTag
                val params = navigation.params

                when (tag) {
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
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder.unbind()
        viewModel.fragmentNavigator.value = null
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun navigateToRouteDetailFragment(vehicleId: Int) {
        viewModel.navigateToRouteDetailFragment(RouteDetailFragmentParam(vehicleId))
    }

    /**
     * Create a new fragment instance and add to the fragment stack
     * if fragment to create either doesn't exist in fragment stack or is not added to the fragment stack
     * We do this to make sure fragment's viewModel is retained on config change,
     * but destroyed as a result of state changes in the parent activity.
     */
    private fun showNewScreen(fragmentProvider: (params: Params?) -> Fragment, params: Params? = null, tag: String) {
        val existingFragment = supportFragmentManager.findFragmentByTag(tag)
        val fragments = supportFragmentManager.fragments

        if (existingFragment == null || !existingFragment.isAdded) {
            if (fragments.isEmpty()) {
                supportFragmentManager.commitNow {
                    replace(R.id.screen_container, fragmentProvider.invoke(params), tag)
                }
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .apply {
                        setCustomAnimations(
                            R.anim.slide_in_left,
                            R.anim.slide_out_left,
                            R.anim.slide_in_right,
                            R.anim.slide_out_right
                        )
                        addToBackStack(tag)
                        replace(R.id.screen_container, fragmentProvider.invoke(params), tag)
                    }
                    .commit()
            }
        }
    }
}
