package logic

import androidx.compose.ui.geometry.Offset
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CollisionDetectorTest {

    private val detector = DefaultCollisionDetector()

    @Test
    fun `checkWallCollision returns true when position is outside left boundary`() {
        val position = Offset(-1f, 5f)
        assertTrue(detector.checkWallCollision(position))
    }

    @Test
    fun `checkWallCollision returns true when position is outside right boundary`() {
        val position = Offset(30f, 5f)
        assertTrue(detector.checkWallCollision(position))
    }

    @Test
    fun `checkWallCollision returns true when position is outside top boundary`() {
        val position = Offset(5f, -1f)
        assertTrue(detector.checkWallCollision(position))
    }

    @Test
    fun `checkWallCollision returns true when position is outside bottom boundary`() {
        val position = Offset(5f, 20f)
        assertTrue(detector.checkWallCollision(position))
    }

    @Test
    fun `checkWallCollision returns false when position is inside boundaries`() {
        val position = Offset(15f, 10f)
        assertFalse(detector.checkWallCollision(position))
    }

    @Test
    fun `checkSelfCollision returns false when snake has only one segment`() {
        val snake = listOf(Offset(5f, 5f))
        assertFalse(detector.checkSelfCollision(snake))
    }

    @Test
    fun `checkSelfCollision returns false when head does not collide with body`() {
        val snake = listOf(
            Offset(5f, 5f),
            Offset(4f, 5f),
            Offset(3f, 5f)
        )
        assertFalse(detector.checkSelfCollision(snake))
    }

    @Test
    fun `checkSelfCollision returns true when head collides with body`() {
        val snake = listOf(
            Offset(5f, 5f),
            Offset(4f, 5f),
            Offset(3f, 5f),
            Offset(5f, 5f)
        )
        assertTrue(detector.checkSelfCollision(snake))
    }

    @Test
    fun `checkCollision returns true when new head hits wall`() {
        val snake = listOf(Offset(0f, 0f))
        val newHead = Offset(-1f, 0f)
        assertTrue(detector.checkCollision(snake, newHead))
    }

    @Test
    fun `checkCollision returns true when new head hits snake body`() {
        val snake = listOf(
            Offset(5f, 5f),
            Offset(4f, 5f),
            Offset(3f, 5f)
        )
        val newHead = Offset(4f, 5f)
        assertTrue(detector.checkCollision(snake, newHead))
    }

    @Test
    fun `checkCollision returns false when new head is in valid position`() {
        val snake = listOf(
            Offset(5f, 5f),
            Offset(4f, 5f)
        )
        val newHead = Offset(6f, 5f)
        assertFalse(detector.checkCollision(snake, newHead))
    }
}
