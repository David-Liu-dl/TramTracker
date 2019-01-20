package au.com.realestate.hometime.helper

import au.com.realestate.infra.android.util.AnimationHelper

class ScreenTestAnimationHelper : AnimationHelper() {

    override val defaultStartOffset: Int
        get() = 0

    override val defaultListItemAnimationStartOffset: Int
        get() = 0

    override val adapterPayloadAnimationDuration: Int
        get() = 0
}
