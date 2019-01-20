package au.com.realestate.hometime.common

import au.com.realestate.domain.route.model.Route
import au.com.realestate.hometime.common.uimodels.RouteHeader

object RouteDirectionComparator: Comparator<RouteHeader> {
    override fun compare(o1: RouteHeader, o2: RouteHeader): Int {
       return o2.direction.getId().compareTo(o1.direction.getId())
    }
}

object RouteComparator: Comparator<Route> {
    override fun compare(o1: Route, o2: Route): Int {
        return o1.getFormattedArrivalDateTime().compareTo(o2.getFormattedArrivalDateTime())
    }
}