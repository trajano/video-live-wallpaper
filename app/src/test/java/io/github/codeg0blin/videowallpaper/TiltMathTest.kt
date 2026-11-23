package io.github.codeg0blin.videowallpaper

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TiltMathTest {
    @Test
    fun referencePoseIsCentered() {
        assertEquals(0f, TiltMath.mapAngle(1.2f, 1.2f, 15), 0.0001f)
    }

    @Test
    fun standardAndExtremePresetsDoNotShareAnArtificialLimit() {
        val moved = Math.toRadians(30.0).toFloat()
        assertEquals(Math.toRadians(15.0).toFloat(), TiltMath.mapAngle(moved, 0f, 15), 0.001f)
        assertEquals(Math.toRadians(80.0).toFloat(), TiltMath.mapAngle(moved, 0f, 80), 0.001f)
        assertEquals(Math.toRadians(89.0).toFloat(), TiltMath.mapAngle(moved, 0f, 89), 0.001f)
        assertEquals(-Math.toRadians(80.0).toFloat(), TiltMath.mapAngle(-moved, 0f, 80), 0.001f)
    }

    @Test
    fun excessiveMovementSaturatesAtConfiguredMaximum() {
        assertEquals(Math.toRadians(80.0).toFloat(), TiltMath.mapAngle(1.4f, 0f, 80), 0.001f)
    }

    @Test
    fun crossingOneHundredEightyDegreesIsSmooth() {
        val start = Math.toRadians(179.0).toFloat()
        val end = Math.toRadians(-179.0).toFloat()
        assertEquals(Math.toRadians(1.0).toFloat(), TiltMath.mapAngle(end, start, 15), 0.001f)
    }

    @Test
    fun uprightPitchDoesNotFlipDirection() {
        val upright = TiltMath.pitch(-9.81f, 0f)
        val pastUpright = TiltMath.pitch(-9.66f, -1.7f)
        assertTrue(pastUpright > upright)
        assertTrue(TiltMath.roll(1f, -9f, 0f) > 0f)
    }
}
