package au.com.realestate.infra.testing

import au.com.realestate.infra.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * SchedulerProvider for unit tests - make sure unit tests run on the same thread
 */
class TestSchedulerProvider : SchedulerProvider {

    override fun ui(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun computation(): Scheduler = Schedulers.trampoline()
}
