package au.com.realestate.infra.util

import au.com.realestate.infra.util.Clock

class RealClock : Clock {

    override val currentTimeMillis: Long
        get() = System.currentTimeMillis()
}
