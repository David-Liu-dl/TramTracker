package au.com.realestate.hometime.timetable.routedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class RouteDetailFragment : Fragment() {

    companion object {
        val TAG: String = RouteDetailFragment::class.java.simpleName

        private const val PARCELABLE_VEHICLE_ID = "PARCELABLE_VEHICLE_ID"

        fun newInstance(vehicleId: Int): RouteDetailFragment {
            return RouteDetailFragment().apply {
                arguments = getBundle(vehicleId)
            }
        }

        private fun getBundle(vehicleId: Int): Bundle {
            return Bundle().apply {
                putInt(PARCELABLE_VEHICLE_ID, vehicleId)
            }
        }
    }

    private val viewModel by viewModel<RouteDetailViewModel>()

    private var vehicleId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vehicleId = arguments?.getInt(PARCELABLE_VEHICLE_ID)
        Timber.i("")
    }
}