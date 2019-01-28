package au.com.realestate.hometime.timetable.routedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.com.realestate.domain.route.model.Route
import au.com.realestate.domain.route.usecase.GetTramByVehicleId
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class RouteDetailViewModel(
    private val getTramByVehicleId: GetTramByVehicleId
) : ViewModel() {

    val route = MutableLiveData<Route?>().apply { value = null }

    val shouldShowErrorDialog = MutableLiveData<Boolean>().apply { value = false }

    private val dispose = CompositeDisposable()

    fun fetchRouteByVehicleId(vehicleId: Int) {
        dispose += getTramByVehicleId.get(GetTramByVehicleId.Params(vehicleId))
            .subscribeBy (
                onSuccess = {
                    route.value = it
                },
                onError = {
                    shouldShowErrorDialog.value = true
                }
            )
    }
}