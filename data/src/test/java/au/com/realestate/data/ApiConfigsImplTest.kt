package au.com.realestate.data

import org.junit.Assert
import org.junit.Test

class ApiConfigsImplTest {

    @Test
    fun `should return the configuration based on BuildConfig`() {
        val config = ApiConfigsImpl()

        Assert.assertEquals(config.baseUrl, BuildConfig.API_BASE_URL)
        Assert.assertEquals(config.networkTimeoutSeconds, BuildConfig.NETWORK_TIMEOUT_SECONDS)
    }
}