package au.com.realestate.infra.extensions

import java.util.*

fun Date.diffWithCurrentTimeInMinutes(): Long {
    val diff = Date().time - this.time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return minutes
}