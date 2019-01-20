package au.com.realestate.domain.route.model

sealed class Direction {
    abstract fun getId(): Int

    object North : Direction() {
        override fun getId(): Int {
            return 4055
        }
    }

    object South : Direction() {
        override fun getId(): Int {
            return 4155
        }
    }
}