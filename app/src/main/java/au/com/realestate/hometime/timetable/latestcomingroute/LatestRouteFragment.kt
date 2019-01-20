package au.com.realestate.hometime.timetable.latestcomingroute

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import au.com.realestate.domain.route.model.Direction
import au.com.realestate.domain.route.model.Route
import au.com.realestate.hometime.R
import au.com.realestate.hometime.common.RouteComparator
import au.com.realestate.hometime.common.RouteDirectionComparator
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class LatestRouteFragment : Fragment() {

    private val viewModel by viewModel<LatestRouteFragmentViewModel>()

    private val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    companion object {
        val TAG: String = LatestRouteFragment::class.java.simpleName

        fun newInstance(): LatestRouteFragment {
            return LatestRouteFragment()
        }
    }

    @BindView(R.id.root_view)
    lateinit var rootView: ViewGroup

    @BindView(R.id.swipe_refresh_layout)
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @BindView(R.id.recycler_view)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.last_updated_time_text_view)
    lateinit var lastUpdatedTimeTextView: TextView

    private lateinit var unbinder: Unbinder

    private lateinit var latestTramRecyclerViewAdapter: LatestRouteRecyclerViewAdapter

    private var errorRefreshingSnackbar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_latest_coming_tram, container, false)
        unbinder = ButterKnife.bind(this, view)

        latestTramRecyclerViewAdapter =
                LatestRouteRecyclerViewAdapter(RouteDirectionComparator, RouteComparator)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@LatestRouteFragment.context)
            adapter = latestTramRecyclerViewAdapter
        }

        swipeRefreshLayout.apply {
            setOnRefreshListener(onSwipeRefreshListener)
            setColorSchemeResources(R.color.colorSecondary)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.lastUpdatedDateTime.observe(this, Observer { lastUpdatedDateTime ->
            lastUpdatedDateTime
                ?.let {
                    val formattedDateTime = dateFormat.format(lastUpdatedDateTime)
                    lastUpdatedTimeTextView.text = getString(R.string.time_last_update_dated, formattedDateTime)
                    lastUpdatedTimeTextView.visibility = View.VISIBLE
                }
                ?: let {
                    lastUpdatedTimeTextView.visibility = View.GONE
                }
        })

        viewModel.tramsWithDirection.observe(this, Observer { tramsWithDirection ->
            latestTramRecyclerViewAdapter.headerWithRoutesMap = tramsWithDirection
        })

        viewModel.shouldPopErrorSnackbar.observe(this, Observer { shouldPopErrorSnackbar ->
            val snackbarAlreadyShown = errorRefreshingSnackbar?.isShownOrQueued ?: false

            if (shouldPopErrorSnackbar && !snackbarAlreadyShown) {
                errorRefreshingSnackbar = Snackbar.make(
                    rootView,
                    getString(R.string.error_message_could_not_refresh_routes),
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.action_retry), retryCallback)
                errorRefreshingSnackbar?.show()
            } else {
                errorRefreshingSnackbar?.dismiss()
            }
        })

        viewModel.refreshing.observe(this, Observer { refreshing ->
            swipeRefreshLayout.isRefreshing = refreshing
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    private val retryCallback: View.OnClickListener = View.OnClickListener {
        viewModel.refreshingRoute()
    }

    private val onSwipeRefreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.refreshingRoute()
    }
}