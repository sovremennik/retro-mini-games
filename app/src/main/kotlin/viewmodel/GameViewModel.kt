package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import logic.*
import model.GRID_COLS
import model.GRID_ROWS
import model.MAX_SPEED
import model.MIN_SPEED
import state.GameState
import state.WaitingState

class GameViewModel(
    private val snakeMovement: SnakeMovement,
    private val collisionDetector: CollisionDetector,
    private val foodGenerator: FoodGenerator
) {
    val snake = mutableStateListOf(Offset(GRID_COLS / 2f, GRID_ROWS / 2f))
    val food = mutableStateOf(foodGenerator.generateFood(snake))
    val direction = mutableStateOf(Direction.RIGHT)
    val isPaused = mutableStateOf(true)
    val gameOver = mutableStateOf(false)
    val speed = mutableStateOf(10)
    var state: GameState = WaitingState()
    private val pendingDirections = mutableStateListOf<Direction>()

    private fun startGame() {
        if (gameOver.value) return
        state.start(this)
    }

    fun restartGame() {
        snake.clear()
        snake.add(Offset(GRID_COLS / 2f, GRID_ROWS / 2f))
        food.value = foodGenerator.generateFood(snake)
        direction.value = Direction.RIGHT
        pendingDirections.clear()
        isPaused.value = true
        gameOver.value = false
        speed.value = 10
        state = WaitingState()
    }

    fun pauseGame() {
        if (!gameOver.value) {
            if (isPaused.value) {
                state.start(this)
            } else {
                state.pause(this)
            }
        }
    }

    fun resumeGame() {
        if (!gameOver.value) {
            state.start(this)
        }
    }

    fun updateSpeed(delta: Int) {
        speed.value = (speed.value + delta).coerceIn(MIN_SPEED, MAX_SPEED)
    }

    fun updateDirection(newDirection: Direction) {
        if (gameOver.value) return

        if (isPaused.value) startGame()

        if (snake.size == 1 || !isOppositeDirection(direction.value, newDirection)) {
            pendingDirections.add(newDirection)
        }
    }

    fun getNextDirection() =
        if (pendingDirections.isNotEmpty()) pendingDirections.removeAt(0)
        else direction.value

    fun moveSnake(onGameOver: () -> Unit) {
        val head = snake.first()
        val newHead = snakeMovement.calculateNewHead(head, direction.value)

        if (collisionDetector.checkCollision(snake, newHead)) {
            onGameOver()
            return
        }

        snake.add(0, newHead)
        snake.removeLast()
    }

    fun checkFoodCollision() {
        val head = snake.first()
        if (head == food.value) {
            snake.add(snake.last())
            food.value = foodGenerator.generateFood(snake)
        }
    }
}
