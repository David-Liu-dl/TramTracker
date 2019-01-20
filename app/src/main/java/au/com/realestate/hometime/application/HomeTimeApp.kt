package au.com.realestate.hometime.application

import android.app.Application
import android.os.Looper
import au.com.realestate.hometime.di.modules
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.android.startKoin
import timber.log.Timber

open class HomeTimeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // ask RxAndroid to use async main thread scheduler
        val asyncMainThreadScheduler = AndroidSchedulers.from(Looper.getMainLooper(), true)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { asyncMainThreadScheduler }

        initKoin()
        initTimber()
    }

    protected open fun initKoin() {
        startKoin(this, modules, logger = TimberLogger())
    }

    protected fun initTimber() {
        Timber.plant(TimberDebugTree())
    }
}