package au.com.realestate.hometime.timetable.latestcomingroute

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import au.com.realestate.domain.route.model.Route
import au.com.realestate.hometime.R
import au.com.realestate.hometime.common.uimodels.RouteHeader
import au.com.realestate.hometime.common.viewholders.RouteHeaderViewHolder
import au.com.realestate.hometime.common.viewholders.RouteItemViewHolder
import kotlin.Comparator

class LatestRouteRecyclerViewAdapter(
    private val routeHeaderComparator: Comparator<RouteHeader>,
    private val routeComparator: Comparator<Route>,
    private val routeClickCallback: (selectedVehicleId: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private sealed class ViewType {
        object Header : ViewType()
        object Item : ViewType()
    }

    var headerWithRoutesMap: Map<RouteHeader, List<Route>> = emptyMap()
        set(value) {
            field = value
            typeViews = getSortedTypeViewsByHeaderWithRoutesMap(value, routeHeaderComparator, routeComparator)
            notifyDataSetChanged()
        }

    private lateinit var typeViews: List<Pair<ViewType, Any>>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.header_route -> RouteHeaderViewHolder(view)
            R.layout.item_route -> RouteItemViewHolder(view)
            else -> throw IllegalArgumentException("Unknown viewType $viewType")
        }
    }

    override fun getItemCount(): Int {
        return typeViews.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.header_route -> {
                (holder as RouteHeaderViewHolder).bind(typeViews[position].second as RouteHeader)
            }
            R.layout.item_route -> {
                (holder as RouteItemViewHolder).bind(typeViews[position].second as Route, routeClickCallback)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (typeViews[position].first) {
            is ViewType.Header -> R.layout.header_route
            is ViewType.Item -> R.layout.item_route
        }
    }

    private fun getSortedTypeViewsByHeaderWithRoutesMap(
        headerWithRoutesMap: Map<RouteHeader, List<Route>>,
        routeHeaderComparator: Comparator<RouteHeader>,
        routeComparator: Comparator<Route>
    ): List<Pair<ViewType, Any>> {
        return headerWithRoutesMap.toSortedMap(routeHeaderComparator)
            .map { headerWithRoutes ->
                headerWithRoutes.value
                    .sortedWith(routeComparator)
                    .map { item -> Pair<ViewType, Any>(ViewType.Item, item) }
                    .toMutableList()
                    .apply {
                        // insert routeHeader to the beginning
                        add(0, Pair(ViewType.Header, headerWithRoutes.key))
                    }
            }
            .flatten()
    }
}