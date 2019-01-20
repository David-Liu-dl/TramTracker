package au.com.realestate.hometime.timetable

import au.com.realestate.hometime.timetable.latestcomingroute.LatestRouteFragment
import au.com.realestate.hometime.timetable.morecomingroute.MoreRouteFragment
import au.com.realestate.uitestingtools.BaseRobot
import au.com.realestate.uitestingtools.RobotActions
import au.com.realestate.uitestingtools.RobotAssertions

fun timetableScreen(block: TimetableRobot.() -> Unit) {
    TimetableRobot().apply { block() }
}

class TimetableRobot : BaseRobot<TimetableRobot.Actions, TimetableRobot.Assertions>(
    Actions(), Assertions()
) {
    class Actions : RobotActions()
    class Assertions : RobotAssertions() {
        fun latestComingTramFragmentDisplayed(){
            fragmentDisplayed<LatestRouteFragment>(LatestRouteFragment.TAG)
        }

        fun moreComingTramFragmentDisplayed(){
            fragmentDisplayed<MoreRouteFragment>(MoreRouteFragment.TAG)
        }
    }

}