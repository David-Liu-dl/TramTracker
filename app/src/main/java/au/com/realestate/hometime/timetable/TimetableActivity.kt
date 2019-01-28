package au.com.realestate.hometime.timetable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.lifecycle.Observer
import au.com.realestate.hometime.R
import au.com.realestate.hometime.timetable.latestcomingroute.LatestRouteFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TimetableActivity : AppCompatActivity() {

    private val viewModel by viewModel<TimetableViewModel>(
        parameters = { parametersOf(LatestRouteFragment.TAG) }
    )

    private val latestComingTramFragmentProvider: () -> LatestRouteFragment = {
        LatestRouteFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)
        viewModel.currentFragmentTag.observe(this, Observer { tag: String ->
            when (tag) {
                LatestRouteFragment.TAG -> {
                    showNewScreen(latestComingTramFragmentProvider, tag)
                }
                else -> {
                    throw RuntimeException("NoSuchFragment $tag")
                }
            }
        })
    }

    /**
     * Create a new fragment instance and add to the fragment stack
     * if fragment to create either doesn't exist in fragment stack or is not added to the fragment stack
     * We do this to make sure fragment's viewModel is retained on config change,
     * but destroyed as a result of state changes in the parent activity.
     */
    private fun showNewScreen(fragmentProvider: () -> Fragment, tag: String) {
        val existingFragment = supportFragmentManager.findFragmentByTag(tag)
        if (existingFragment == null || !existingFragment.isAdded) {
            supportFragmentManager.commitNow {
                replace(R.id.screen_container, fragmentProvider.invoke(), tag)
            }
        }
    }
}
