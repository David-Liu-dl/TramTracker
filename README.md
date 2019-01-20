# HomeTime - Android

[![Android API](https://img.shields.io/badge/API-22%2B-blue.svg?style=flat-square&label=API&maxAge=300)](https://www.android.com/history/)

## Materials and Attachments

* For ```screenshot and video```, please find them in the **docs** folder in root project dir.
* Please find the generated reports in ```jacocoTestReport``` folder under root project dir.
## Architecture Guideline

This app uses **[Clean Architecture](http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)** with 3 main architectural layers: **Presentation (app), domain and data**

### app

* This is the core application module with platform (Android) dependencies
* Integrates all other layers and does dependency injection
* Includes the presentation layer which follows MVVM architectural concept
* It uses Android Architechtural Components: **ViewModel** and **LiveData**

### domain

* This is a pure JVM library
* Contains business logic - use cases (interactors)
* Includes domain models and repository interfaces

### data

* This is an Android library with platform (Android) dependencies
* Provides implementation for fetching (and pushing) data from various external sources (disk, remote)
* Uses repository pattern
* Depends on the **domain** module (implements repository interfaces)
 
There are additional library modules for things such as infrastructure, utils and helpers which are generally **not** specific to the app itself.

## Build Variants and Product Flavors

We have 3 product flavors (based on usual dev environment):
* `mock`: uses mock API endpoints - used for local development
* `staging`: uses staging endpoints (currently, same as prod in this project)- used for both local development and internal (alpha) testing
* `prod`: uses production endpoints - the final deliverable published on Play Store

## Build Versioning

We use `<major>.<minor>.<patch>.<build>` versioning scheme for build artifact (apk), where `<build>` is the build number provided by CI tools as environment variable
```
 versionCode = (<major> * 10000) + (<minor> * 100) + <patch>
 versionName = <major>.<minor>.<patch>.<build>
```
## Project Implementation Detail
**Funtionality:** 
This application only has one screen with the following interaction and information
   * Interaction
        * Swipe to refresh
   * Information
        * Last update time (formatted in: ``` Last updated on: h:mm a ```)
        * Direction (``` North ``` or ```South ```)
        * Tram Number 
        * Destination (```Balaclava``` or ```North Richmond```)
        * Time remains (formatted in: ```in %d mins```)
        * Special event (``` plain text```)


**Error handling:**      
Based on the API structure of Tram Tracker, every response should have the following format 
```java
@JsonClass(generateAdapter = true)
class HttpResponse<T>(
    @Json(name = "errorMessage")
    val errorMessage: String? = null,
    @Json(name = "hasError")
    val hasError: Boolean,
    @Json(name = "hasResponse")
    val hasResponse: Boolean? = null,
    @Json(name = "responseObject")
    val responseObject: T? = null,
    @Json(name = "timeRequested")
    val timeRequested: String,
    @Json(name = "timeResponded")
    val timeResponded: String,
    @Json(name = "webMethodCalled")
    val webMethodCalled: String
)
 ```

As we don't have enough http error information, the error handler is very simple which will determine and throw the error
```java
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
```
 
## unitTest and androidTest
This project has integrated jacocoPlugin to help generate reports for different flavors. Currently, we only care **mockDebug** reports for **app** and **data**, and the **tests** for **domain** (JVM module). 
* As our business logics are mainly located in **domain** and **data**, the tests should cover all those two modules. 
* For the presentation layer, tests for **viewModel** is necessary and essential.

