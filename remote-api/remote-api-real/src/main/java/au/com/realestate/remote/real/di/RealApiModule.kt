package au.com.realestate.remote.real.di

import au.com.realestate.remote.ApiConfigs
import au.com.realestate.remote.auth.api.AuthService
import au.com.realestate.remote.tram.api.TramService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val realApiModule = module {

    single {
        Moshi.Builder().build()
    }

    single {
        val apiConfigs: ApiConfigs = get()
        OkHttpClient.Builder()
            .apply {
                callTimeout(apiConfigs.networkTimeoutSeconds, TimeUnit.SECONDS)
                // add logging interceptor
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            }.build()
    }

    single {
        val apiConfigs: ApiConfigs = get()
        Retrofit.Builder().apply {
            baseUrl(apiConfigs.baseUrl)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(MoshiConverterFactory.create(get()))
            client(get())
        }.build()
    }

    single{
        get<Retrofit>().create(AuthService::class.java)
    }

    single{
        get<Retrofit>().create(TramService::class.java)
    }
}
