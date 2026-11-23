package io.github.codeg0blin.videowallpaper

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

/** Converts gravity-sensor readings into bounded, relative perspective angles. */
internal object TiltMath {
    // A 30-degree physical movement reaches the user-selected visual maximum.
    private val inputRangeRadians = Math.toRadians(30.0).toFloat()

    // atan2(-y, z) stays continuous when the phone passes through upright.
    fun pitch(y: Float, z: Float): Float = atan2(-y, z)

    fun roll(x: Float, y: Float, z: Float): Float = atan2(x, hypot(y, z))

    fun mapAngle(current: Float, reference: Float, maxDegrees: Int): Float {
        // Subtracting Euler angles directly creates a jump at +180/-180.
        val delta = current - reference
        val wrapped = atan2(sin(delta), cos(delta))
        val normalized = (wrapped / inputRangeRadians).coerceIn(-1f, 1f)
        return normalized * Math.toRadians(maxDegrees.coerceIn(1, 89).toDouble()).toFloat()
    }
}
