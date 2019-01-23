package au.com.realestate.hometime.timetable.latestcomingroute

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.com.realestate.domain.route.model.Direction
import au.com.realestate.domain.route.model.Route
import au.com.realestate.domain.route.usecase.GetComingTrams
import au.com.realestate.hometime.common.uimodels.RouteHeader
import au.com.realestate.infra.util.Clock
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import java.util.*

const val routeId = 78

class LatestRouteFragmentViewModel(
    private val getComingTrams: GetComingTrams,
    private val clock: Clock
) : ViewModel() {

    val tramsWithDirection = MutableLiveData<Map<RouteHeader, List<Route>>>().apply { value = emptyMap() }
    val lastUpdatedDateTime = MutableLiveData<Date>().apply { value = null }
    val refreshing = MutableLiveData<Boolean>().apply { value = false }
    val shouldPopErrorSnackbar = MutableLiveData<Boolean>().apply { value = false }

    private val disposables = CompositeDisposable()

    fun refreshingRoute() {
        shouldPopErrorSnackbar.value = false
        refreshing.value = true
        disposables += getComingTrams.get(GetComingTrams.Params(Direction.North))
            .zipWith(getComingTrams.get(GetComingTrams.Params(Direction.South)))
            .subscribeBy(
                onSuccess = {
                    tramsWithDirection.value = mapOf(
                        RouteHeader(Direction.North, routeId) to it.first,
                        RouteHeader(Direction.South, routeId) to it.second
                    )
                    lastUpdatedDateTime.value = Date(clock.currentTimeMillis)
                    refreshing.value = false
                },
                onError = {
                    refreshing.value = false
                    shouldPopErrorSnackbar.value = true
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}