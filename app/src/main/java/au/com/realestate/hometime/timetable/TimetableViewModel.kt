package au.com.realestate.hometime.timetable

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.com.realestate.hometime.timetable.routedetails.RouteDetailFragment

class TimetableViewModel(
    initialNavigation: Navigation
): ViewModel(){
    val fragmentNavigator = MutableLiveData<Navigation>().apply { value = initialNavigation }

    fun navigateToRouteDetailFragment(routeDetailFragmentParam: RouteDetailFragmentParam){
        fragmentNavigator.value = Navigation(RouteDetailFragment.TAG, routeDetailFragmentParam)
    }
}

data class Navigation(val fragmentTag: String, val params: Params)

class RouteDetailFragmentParam(val selectedVehicleId: Int): Params()

class EmptyParams: Params()

open class Params
