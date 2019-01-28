package au.com.realestate.hometime.timetable.routedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import au.com.realestate.hometime.R
import au.com.realestate.infra.extensions.diffWithCurrentTimeInMinutes
import butterknife.BindView
import butterknife.ButterKnife
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    @BindView(R.id.direction_text_view)
    lateinit var directionTextView: TextView

    @BindView(R.id.time_remain_text_view)
    lateinit var timeRemainTextView: TextView

    @BindView(R.id.special_event_text_view)
    lateinit var specialEventTextView: TextView

    private val viewModel by viewModel<RouteDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getInt(PARCELABLE_VEHICLE_ID)
            ?.run {
                viewModel.fetchRouteByVehicleId(this)
            }
            ?: let {
                throw RuntimeException("PARCELABLE_VEHICLE_ID cannot be found")
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_route_detail, container, false)

        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.route.observe(this, Observer { route ->
            route?.let {
                directionTextView.text = route.destination
                timeRemainTextView.text = getString(
                    R.string.label_time_remain,
                    route.getFormattedArrivalDateTime().diffWithCurrentTimeInMinutes()
                )

                if (route.hasSpecialEvent) {
                    specialEventTextView.text = route.specialEventMessage
                } else {
                    specialEventTextView.text = getString(R.string.label_no_special_event)
                }
            }
        })
    }
}