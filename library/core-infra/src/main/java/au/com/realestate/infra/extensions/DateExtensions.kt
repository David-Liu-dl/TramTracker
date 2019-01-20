package au.com.realestate.infra.extensions

import java.util.*

fun Date.diffWithCurrentTimeInMinutes(): Long {
    val diff = this.time - Date().time
    val seconds = diff / 1000

    return seconds / 60
}