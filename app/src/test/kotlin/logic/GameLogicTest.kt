package logic

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameLogicTest {

    @Test
    fun `isOppositeDirection returns true for UP and DOWN`() {
        assertTrue(isOppositeDirection(Direction.UP, Direction.DOWN))
        assertTrue(isOppositeDirection(Direction.DOWN, Direction.UP))
    }

    @Test
    fun `isOppositeDirection returns true for LEFT and RIGHT`() {
        assertTrue(isOppositeDirection(Direction.LEFT, Direction.RIGHT))
        assertTrue(isOppositeDirection(Direction.RIGHT, Direction.LEFT))
    }

    @Test
    fun `isOppositeDirection returns false for same direction`() {
        assertFalse(isOppositeDirection(Direction.UP, Direction.UP))
        assertFalse(isOppositeDirection(Direction.DOWN, Direction.DOWN))
        assertFalse(isOppositeDirection(Direction.LEFT, Direction.LEFT))
        assertFalse(isOppositeDirection(Direction.RIGHT, Direction.RIGHT))
    }

    @Test
    fun `isOppositeDirection returns false for perpendicular directions`() {
        assertFalse(isOppositeDirection(Direction.UP, Direction.LEFT))
        assertFalse(isOppositeDirection(Direction.UP, Direction.RIGHT))
        assertFalse(isOppositeDirection(Direction.DOWN, Direction.LEFT))
        assertFalse(isOppositeDirection(Direction.DOWN, Direction.RIGHT))
        assertFalse(isOppositeDirection(Direction.LEFT, Direction.UP))
        assertFalse(isOppositeDirection(Direction.LEFT, Direction.DOWN))
        assertFalse(isOppositeDirection(Direction.RIGHT, Direction.UP))
        assertFalse(isOppositeDirection(Direction.RIGHT, Direction.DOWN))
    }
}
