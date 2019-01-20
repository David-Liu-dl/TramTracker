package au.com.realestate.hometime.di

import au.com.realestate.hometime.timetable.TimetableViewModel
import au.com.realestate.hometime.timetable.latestcomingroute.LatestRouteFragmentViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModules = listOf(
    module {
        viewModel { (initialFragmentTag: String) ->
            TimetableViewModel(initialFragmentTag)
        }
    },
    module {
        viewModel {
            LatestRouteFragmentViewModel(
                getSouthComingTrams = get(),
                getNorthComingTrams = get(),
                clock = get()
            )
        }
    }
)