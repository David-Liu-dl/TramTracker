package au.com.realestate.hometime.timetable

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test

class TimetableViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `should has initial value for currentFragmentTag`() {
        val initialFragmentTag = "LatestComingTramFragment"
        val viewModel = TimetableViewModel(initialFragmentTag)

        viewModel.currentFragmentTag.value.shouldEqual(initialFragmentTag)
    }

}