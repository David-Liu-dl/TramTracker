package au.com.realestate.hometime.timetable

import au.com.realestate.hometime.BaseScreenTest
import org.junit.Test

class TimetableScreenTest : BaseScreenTest() {

    @Test
    fun openTimetableScreenWithLatestComingTramFragment_correctDisplayed() {
        timetableScreen {
            perform {
                launchActivityScenario<TimetableActivity>()
            }
            check {
                latestComingTramFragmentDisplayed()
            }
        }
    }
}