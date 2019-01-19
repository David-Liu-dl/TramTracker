package au.com.realestate.data.di

import au.com.realestate.data.ApiConfigsImpl
import au.com.realestate.remote.ApiConfigs
import org.koin.dsl.module.module

val dataModule = module {
    single<ApiConfigs>{
        ApiConfigsImpl()
    }
}