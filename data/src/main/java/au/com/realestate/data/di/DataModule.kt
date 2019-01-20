package au.com.realestate.data.di

import au.com.realestate.data.ApiConfigsImpl
import au.com.realestate.data.route.RouteRepositoryImpl
import au.com.realestate.domain.route.repository.RouteRepository
import au.com.realestate.remote.ApiConfigs
import au.com.realestate.remote.HttpExceptionDefiner
import org.koin.dsl.module.module

val dataModule = module {
    single<ApiConfigs> {
        ApiConfigsImpl()
    }

    single<RouteRepository> {
        RouteRepositoryImpl(
            tramService = get(),
            httpErrorDefiner = get()
        )
    }

    single {
        HttpExceptionDefiner()
    }
}