package ui

import androidx.compose.ui.input.key.Key
import logic.Direction
import model.SPEED_STEP

fun handleInput(
    key: Key,
    gameOver: Boolean,
    restart: () -> Unit,
    resumeGame: () -> Unit,
    togglePause: () -> Unit,
    setDirection: (Direction) -> Unit,
    updateSpeed: (delta: Int) -> Unit
): Boolean = when (key) {
    Key(49) -> { // key "1"
        updateSpeed(-SPEED_STEP)
        true
    }

    Key(50) -> { // key "2"
        updateSpeed(SPEED_STEP)
        true
    }

    Key.PageDown -> {
        updateSpeed(-SPEED_STEP)
        true
    }

    Key.PageUp -> {
        updateSpeed(SPEED_STEP)
        true
    }

    Key.DirectionUp, Key.W -> {
        setDirection(Direction.UP)
        resumeGame()
        true
    }

    Key.DirectionDown, Key.S -> {
        setDirection(Direction.DOWN)
        resumeGame()
        true
    }

    Key.DirectionLeft, Key.A -> {
        setDirection(Direction.LEFT)
        resumeGame()
        true
    }

    Key.DirectionRight, Key.D -> {
        setDirection(Direction.RIGHT)
        resumeGame()
        true
    }

    Key.Spacebar -> {
        togglePause()
        true
    }

    Key.R -> {
        if (gameOver) {
            restart()
            true
        } else false
    }

    else -> false
}
