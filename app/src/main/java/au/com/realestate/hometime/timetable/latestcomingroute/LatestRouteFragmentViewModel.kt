package au.com.realestate.hometime.timetable.latestcomingroute

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.com.realestate.domain.route.model.Direction
import au.com.realestate.domain.route.model.Route
import au.com.realestate.domain.route.usecase.GetNorthComingTrams
import au.com.realestate.domain.route.usecase.GetSouthComingTrams
import au.com.realestate.hometime.common.uimodels.RouteHeader
import au.com.realestate.infra.util.Clock
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
import java.time.LocalDateTime
import java.util.*

const val routeId = 78

class LatestRouteFragmentViewModel(
    private val getSouthComingTrams: GetSouthComingTrams,
    private val getNorthComingTrams: GetNorthComingTrams,
    private val clock: Clock
) : ViewModel() {

    val tramsWithDirection = MutableLiveData<Map<RouteHeader, List<Route>>>().apply { value = emptyMap() }
    val lastUpdatedDateTime = MutableLiveData<Date>().apply { value = null }
    val refreshing = MutableLiveData<Boolean>().apply { value = false }
    val shouldPopErrorSnackbar = MutableLiveData<Boolean>().apply { value = false }

    private val disposables = CompositeDisposable()

    init {
        refreshingRoute()
    }

    fun refreshingRoute() {
        shouldPopErrorSnackbar.value = false
        refreshing.value = true
        disposables += getSouthComingTrams.get()
            .zipWith(getNorthComingTrams.get())
            .subscribeBy(
                onSuccess = {
                    tramsWithDirection.value = mapOf(
                        RouteHeader(Direction.South, routeId) to it.first,
                        RouteHeader(Direction.North, routeId) to it.second
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