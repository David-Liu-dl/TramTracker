package au.com.realestate.remote.mock.di

import au.com.realestate.remote.ApiConfigs
import au.com.realestate.remote.auth.api.AuthService
import au.com.realestate.remote.mock.auth.api.MockAuthService
import au.com.realestate.remote.mock.tram.api.MockTramService
import au.com.realestate.remote.tram.api.TramService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit

private const val MOCK_SERVER_PORT = 5000
private const val DUMMY_URL = "http://localhost:$MOCK_SERVER_PORT/"

val mockApiModule = module {

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
        Retrofit.Builder().apply {
            baseUrl(DUMMY_URL)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            client(get())
        }.build()
    }

    single {
        NetworkBehavior.create().apply {
            // make sure behavior is deterministic
            setVariancePercent(0)
            // no delay by default
            setDelay(0, TimeUnit.MILLISECONDS)
            // no failure by default
            setFailurePercent(0)
        }
    }

    single {
        MockAuthService(
            MockRetrofit.Builder(get())
                .let { builder ->
                    builder.networkBehavior(get())
                    builder.build().create(AuthService::class.java)
                }
        )
    }

    single {
        MockTramService(
            MockRetrofit.Builder(get())
                .let { builder ->
                    builder.networkBehavior(get())
                    builder.build().create(TramService::class.java)
                }
        )
    }
}