package au.com.realestate.hometime.di

import au.com.realestate.data.di.dataModule
import au.com.realestate.domain.di.domainModule
import au.com.realestate.hometime.apiModule
import au.com.realestate.infra.SchedulerProvider
import au.com.realestate.infra.android.rx.AndroidSchedulerProvider
import au.com.realestate.infra.android.util.AnimationHelper
import au.com.realestate.infra.util.Clock
import au.com.realestate.infra.util.RealClock
import org.koin.dsl.module.module
import org.koin.experimental.builder.single

val appModule = module {
    single<SchedulerProvider> { AndroidSchedulerProvider() }

    single<Clock> { RealClock() }

    single<AnimationHelper>()
}

val modules = viewModules + listOf(
    appModule,
    domainModule,
    dataModule,
    apiModule
)