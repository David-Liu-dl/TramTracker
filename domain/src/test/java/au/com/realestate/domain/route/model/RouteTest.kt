package au.com.realestate.domain.route.model

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class RouteTest {
    private val dummyDateTimeMillisecond = 1547858980631
    private val dummyPredictedArrivalDateTime = "/Date($dummyDateTimeMillisecond+1100)/"

    @Test
    fun `should get correct formatted time`() {
        val route = Route(true, 78, "HomeTime", dummyPredictedArrivalDateTime, null)
        val parsedDate = route.getFormattedArrivalDateTime()

        assertEquals(parsedDate, Date(dummyDateTimeMillisecond))
    }
}