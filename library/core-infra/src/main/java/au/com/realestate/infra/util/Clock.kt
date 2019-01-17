package au.com.realestate.infra.util

interface Clock {

    /**
     * Returns the current time in milliseconds UTC.
     * @return
     */
    val currentTimeMillis: Long
}
