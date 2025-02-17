package viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import logic.Direction
import logic.isOppositeDirection
import logic.randomFoodPosition
import model.GRID_COLS
import model.GRID_ROWS
import model.MAX_SPEED
import model.MIN_SPEED
import state.GameState
import state.WaitingState

class GameViewModel {
    val snake = mutableStateListOf(Offset(GRID_COLS / 2f, GRID_ROWS / 2f))
    val food = mutableStateOf(randomFoodPosition(snake))
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
        food.value = randomFoodPosition(snake)
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
}
