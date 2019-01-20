package au.com.realestate.data

import au.com.realestate.remote.HttpResponse
import java.lang.Exception

class HttpExceptionDefiner {
    sealed class HttpException : Exception() {
        data class TramTrackerException(val tramTrackerMessage: String?) : HttpException()
    }

    fun <T> defineHttpException(httpResponse: HttpResponse<T>?): HttpResponse<T> {
        httpResponse?.let {
            if (it.hasError) {
                throw HttpException.TramTrackerException(it.errorMessage)
            }
            if (httpResponse.responseObject == null){
                throw HttpException.TramTrackerException(null)
            }

            return it
        }
            ?: let {
                throw HttpException.TramTrackerException(null)
            }
    }
}