package au.com.realestate.data

import au.com.realestate.remote.ApiConfigs

class ApiConfigsImpl : ApiConfigs {
    override val baseUrl: String
        get() = BuildConfig.API_BASE_URL
    override val networkTimeoutSeconds: Long
        get() = BuildConfig.NETWORK_TIMEOUT_SECONDS
}