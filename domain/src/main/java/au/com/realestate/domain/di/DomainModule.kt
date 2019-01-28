package au.com.realestate.domain.di

import au.com.realestate.domain.route.usecase.GetComingTrams
import au.com.realestate.domain.route.usecase.GetTramByVehicleId
import org.koin.dsl.module.module
import org.koin.experimental.builder.single

val domainModule = module {
    single<GetComingTrams>()

    single<GetTramByVehicleId>()
}
