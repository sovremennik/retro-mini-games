package viewmodel

import androidx.compose.ui.geometry.Offset
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.CollisionDetector
import logic.DefaultCollisionDetector
import logic.DefaultFoodGenerator
import logic.DefaultSnakeMovement
import logic.Direction
import logic.FoodGenerator
import logic.SnakeMovement

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameViewModelTest {

    private fun createViewModel(
        movement: SnakeMovement = DefaultSnakeMovement(),
        detector: CollisionDetector = DefaultCollisionDetector(),
        generator: FoodGenerator = DefaultFoodGenerator()
    ) = GameViewModel(movement, detector, generator)

    @Test
    fun `initial state is correct`() {
        val viewModel = createViewModel()

        assertEquals(1, viewModel.snake.size)
        assertEquals(Direction.RIGHT, viewModel.direction.value)
        assertTrue(viewModel.isPaused.value)
        assertFalse(viewModel.gameOver.value)
        assertEquals(10, viewModel.speed.value)
    }

    @Test
    fun `restartGame resets all state`() {
        val viewModel = createViewModel()

        viewModel.snake.add(Offset(16f, 10f))
        viewModel.direction.value = Direction.UP
        viewModel.isPaused.value = false
        viewModel.gameOver.value = true
        viewModel.speed.value = 15

        viewModel.restartGame()

        assertEquals(1, viewModel.snake.size)
        assertEquals(Direction.RIGHT, viewModel.direction.value)
        assertTrue(viewModel.isPaused.value)
        assertFalse(viewModel.gameOver.value)
        assertEquals(10, viewModel.speed.value)
    }

    @Test
    fun `updateSpeed respects MIN and MAX boundaries`() {
        val viewModel = createViewModel()

        viewModel.updateSpeed(-20)
        assertEquals(2, viewModel.speed.value)

        viewModel.updateSpeed(100)
        assertEquals(20, viewModel.speed.value)
    }

    @Test
    fun `updateDirection adds direction to pending when valid`() {
        val viewModel = createViewModel()

        viewModel.updateDirection(Direction.UP)

        val nextDirection = viewModel.getNextDirection()
        assertEquals(Direction.UP, nextDirection)
    }

    @Test
    fun `updateDirection ignores opposite direction`() {
        val viewModel = createViewModel()
        viewModel.direction.value = Direction.RIGHT
        viewModel.isPaused.value = false

        viewModel.snake.add(Offset(16f, 10f))

        viewModel.updateDirection(Direction.LEFT)

        val nextDirection = viewModel.getNextDirection()
        assertEquals(Direction.RIGHT, nextDirection)
    }

    @Test
    fun `moveSnake calls onGameOver when collision detected`() {
        val detector = mockk<CollisionDetector>()
        val movement = mockk<SnakeMovement>()

        every { movement.calculateNewHead(any(), any()) } returns Offset(-1f, 10f)
        every { detector.checkCollision(any(), any()) } returns true

        val viewModel = createViewModel(movement = movement, detector = detector)

        var gameOverCalled = false
        viewModel.moveSnake { gameOverCalled = true }

        assertTrue(gameOverCalled)
    }

    @Test
    fun `checkFoodCollision grows snake when food is eaten`() {
        val generator = mockk<FoodGenerator>()
        every { generator.generateFood(any()) } returns Offset(20f, 15f)

        val viewModel = createViewModel(generator = generator)

        viewModel.food.value = viewModel.snake.first()
        val initialSize = viewModel.snake.size

        viewModel.checkFoodCollision()

        assertEquals(initialSize + 1, viewModel.snake.size)
        verify { generator.generateFood(any()) }
    }

    @Test
    fun `pauseGame toggles pause state`() {
        val viewModel = createViewModel()

        assertTrue(viewModel.isPaused.value)

        viewModel.pauseGame()
        assertFalse(viewModel.isPaused.value)

        viewModel.pauseGame()
        assertTrue(viewModel.isPaused.value)
    }

    @Test
    fun `resumeGame unpauses the game`() {
        val viewModel = createViewModel()

        assertTrue(viewModel.isPaused.value)

        viewModel.resumeGame()

        assertFalse(viewModel.isPaused.value)
    }

    @Test
    fun `getNextDirection returns current direction when no pending directions`() {
        val viewModel = createViewModel()
        viewModel.direction.value = Direction.UP

        assertEquals(Direction.UP, viewModel.getNextDirection())
    }

    @Test
    fun `getNextDirection returns and removes first pending direction`() {
        val viewModel = createViewModel()

        viewModel.updateDirection(Direction.UP)
        viewModel.updateDirection(Direction.RIGHT)

        assertEquals(Direction.UP, viewModel.getNextDirection())
        assertEquals(Direction.RIGHT, viewModel.getNextDirection())
        assertEquals(viewModel.direction.value, viewModel.getNextDirection())
    }
}
