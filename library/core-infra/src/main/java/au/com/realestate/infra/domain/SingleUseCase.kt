package au.com.realestate.infra.domain

import au.com.realestate.infra.SchedulerProvider
import io.reactivex.Single

/**
 * Abstract class for a use case, representing an execution unit of asynchronous work.
 * This use case type uses [Single] as the return type.
 * Upon subscription a use case will execute its job in the io thread of the schedulerProvider
 * and will post the result in the UI thread.
 */
abstract class SingleUseCase<P : UseCaseParams, T>(private val schedulerProvider: SchedulerProvider) {

    lateinit var params: P

    protected abstract fun createUseCase(): Single<T>

    /**
     * Return the created use case single with the provided execution thread and post execution thread
     * @return
     */
    fun get(params: P): Single<T> {
        this.params = params
        // update params for the next execution
        return createUseCase()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

}
