package au.com.realestate.hometime.helper

import android.os.AsyncTask
import au.com.realestate.infra.android.rx.AndroidSchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * SchedulerProvider for screen tests
 */
class ScreenTestSchedulerProvider : AndroidSchedulerProvider() {

    override fun io(): Scheduler {
        // override default Schedulers.io() with AsyncTask.THREAD_POOL_EXECUTOR
        // as Espresso by default waits for async tasks to complete
        return Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}
