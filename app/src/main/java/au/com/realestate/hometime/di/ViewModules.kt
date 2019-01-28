package au.com.realestate.hometime.di

import au.com.realestate.hometime.timetable.Navigation
import au.com.realestate.hometime.timetable.TimetableViewModel
import au.com.realestate.hometime.timetable.latestcomingroute.LatestRouteFragmentViewModel
import au.com.realestate.hometime.timetable.routedetails.RouteDetailViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModules = listOf(
    module {
        viewModel { (initialNavigation: Navigation) ->
            TimetableViewModel(initialNavigation)
        }
    },
    module {
        viewModel {
            LatestRouteFragmentViewModel(
                getComingTrams = get(),
                clock = get()
            )
        }
    },
    module {
        viewModel {
            RouteDetailViewModel(get())
        }
    }
)