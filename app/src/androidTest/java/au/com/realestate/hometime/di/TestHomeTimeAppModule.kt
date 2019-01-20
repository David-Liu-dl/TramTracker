package au.com.realestate.hometime.di

import au.com.realestate.data.di.dataModule
import au.com.realestate.domain.di.domainModule
import au.com.realestate.hometime.helper.ScreenTestAnimationHelper
import au.com.realestate.hometime.helper.ScreenTestSchedulerProvider
import au.com.realestate.infra.SchedulerProvider
import au.com.realestate.infra.android.util.AnimationHelper
import au.com.realestate.infra.util.Clock
import au.com.realestate.infra.util.RealClock
import org.koin.dsl.module.module

val testAppModule = module {

    single<SchedulerProvider> { ScreenTestSchedulerProvider() }

    single<Clock> { RealClock() }

    single<AnimationHelper> { ScreenTestAnimationHelper() }
}

val testModules = viewModules + listOf(
    testAppModule,
    domainModule,
    dataModule,
    testApiModule
)
