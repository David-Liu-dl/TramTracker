package au.com.realestate.data

import au.com.realestate.remote.HttpResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class HttpExceptionDefinerTest {

    private val httpExceptionDefiner = HttpExceptionDefiner()
    private val input = mockk<HttpResponse<Any>>()

    @Test
    fun `should throw HttpException when hasError is true`() {
        every { input.hasError } returns true
        every { input.responseObject } returns null
        every { input.errorMessage } returns null

        assertFailsWith(HttpExceptionDefiner.HttpException.TramTrackerException(null)::class) {
            httpExceptionDefiner.defineHttpException(input)
        }
    }

    @Test
    fun `should throw HttpException when responseObject is null`() {
        every { input.hasError } returns false
        every { input.responseObject } returns null
        every { input.errorMessage } returns null

        assertFailsWith(HttpExceptionDefiner.HttpException.TramTrackerException(null)::class) {
            httpExceptionDefiner.defineHttpException(input)
        }
    }

    @Test
    fun `should return input when hasError is false and responseObject is not null`() {
        every { input.hasError } returns false
        every { input.responseObject } returns emptyList<Any>()
        every { input.errorMessage } returns null

        assertEquals(input, httpExceptionDefiner.defineHttpException(input))
    }
}