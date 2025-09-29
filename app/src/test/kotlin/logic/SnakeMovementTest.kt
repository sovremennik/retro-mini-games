package logic

import androidx.compose.ui.geometry.Offset
import kotlin.test.Test
import kotlin.test.assertEquals

class SnakeMovementTest {

    private val movement = DefaultSnakeMovement()

    @Test
    fun `calculateNewHead moves up correctly`() {
        val head = Offset(5f, 5f)
        val newHead = movement.calculateNewHead(head, Direction.UP)
        assertEquals(Offset(5f, 4f), newHead)
    }

    @Test
    fun `calculateNewHead moves down correctly`() {
        val head = Offset(5f, 5f)
        val newHead = movement.calculateNewHead(head, Direction.DOWN)
        assertEquals(Offset(5f, 6f), newHead)
    }

    @Test
    fun `calculateNewHead moves left correctly`() {
        val head = Offset(5f, 5f)
        val newHead = movement.calculateNewHead(head, Direction.LEFT)
        assertEquals(Offset(4f, 5f), newHead)
    }

    @Test
    fun `calculateNewHead moves right correctly`() {
        val head = Offset(5f, 5f)
        val newHead = movement.calculateNewHead(head, Direction.RIGHT)
        assertEquals(Offset(6f, 5f), newHead)
    }

    @Test
    fun `moveSnake adds new head and removes tail`() {
        val snake = listOf(
            Offset(5f, 5f),
            Offset(4f, 5f),
            Offset(3f, 5f)
        )
        val newHead = Offset(6f, 5f)
        val result = movement.moveSnake(snake, newHead)

        assertEquals(3, result.size)
        assertEquals(Offset(6f, 5f), result[0])
        assertEquals(Offset(5f, 5f), result[1])
        assertEquals(Offset(4f, 5f), result[2])
    }

    @Test
    fun `moveSnake works with single segment snake`() {
        val snake = listOf(Offset(5f, 5f))
        val newHead = Offset(6f, 5f)
        val result = movement.moveSnake(snake, newHead)

        assertEquals(1, result.size)
        assertEquals(Offset(6f, 5f), result[0])
    }
}
