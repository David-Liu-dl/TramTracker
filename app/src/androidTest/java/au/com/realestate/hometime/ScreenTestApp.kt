package au.com.realestate.hometime

import au.com.realestate.hometime.application.HomeTimeApp
import au.com.realestate.hometime.di.testModules
import org.koin.android.ext.android.startKoin
import org.koin.log.EmptyLogger

class ScreenTestApp: HomeTimeApp() {

    override fun initKoin() {
        startKoin(this, testModules, logger = EmptyLogger())
    }

}