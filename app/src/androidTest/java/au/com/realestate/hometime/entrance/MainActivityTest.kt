package au.com.realestate.hometime.entrance

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import au.com.realestate.hometime.BaseScreenTest
import org.junit.Test

@LargeTest
class MainActivityTest: BaseScreenTest(){

    @Test
    fun openMainActivity_NotNull(){
        launchActivityScenario<MainActivity>()
        onView(ViewMatchers.withText("South"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}