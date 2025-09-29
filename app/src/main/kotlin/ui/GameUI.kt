package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import di.GameDependencies
import kotlinx.coroutines.delay
import model.COLOR_BACKGROUND
import model.GRID_CELL_SIZE_DP
import model.GRID_COLS
import model.GRID_ROWS
import model.UI_PANEL_WIDTH_DP

import viewmodel.GameViewModel

@Composable
fun snakeGame() {
    val focusRequester = remember { FocusRequester() }
    val gameViewModel = remember { GameDependencies.createGameViewModel() }

    setupFocus(focusRequester)
    startGameLoop(gameViewModel)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(COLOR_BACKGROUND)
            .focusRequester(focusRequester)
            .focusable()
            .onKeyEvent { handleKeyEvent(it, gameViewModel, focusRequester) }
    ) {
        gameLayout(gameViewModel, focusRequester)
    }
}

@Composable
private fun setupFocus(focusRequester: FocusRequester) {
    LaunchedEffect(Unit) { focusRequester.requestFocus() }
}

@Composable
private fun startGameLoop(gameViewModel: GameViewModel) {
    LaunchedEffect(gameViewModel.gameOver.value, gameViewModel.speed.value) {
        while (!gameViewModel.gameOver.value) {
            if (!gameViewModel.isPaused.value) {
                gameViewModel.direction.value = gameViewModel.getNextDirection()
                gameViewModel.moveSnake {
                    gameViewModel.gameOver.value = true
                }
                gameViewModel.checkFoodCollision()
            }
            delay(1000L / gameViewModel.speed.value)
        }
    }
}

private fun handleKeyEvent(
    event: KeyEvent,
    gameViewModel: GameViewModel,
    focusRequester: FocusRequester
): Boolean {
    if (event.type != KeyEventType.KeyDown) return false

    return handleInput(
        key = event.key,
        gameOver = gameViewModel.gameOver.value,
        restart = {
            gameViewModel.restartGame()
            focusRequester.requestFocus()
        },
        resumeGame = { gameViewModel.resumeGame() },
        togglePause = { gameViewModel.pauseGame() },
        setDirection = { gameViewModel.updateDirection(it) },
        updateSpeed = { delta -> gameViewModel.updateSpeed(delta) }
    )
}

@Composable
private fun gameLayout(gameViewModel: GameViewModel, focusRequester: FocusRequester) {
    Row(modifier = Modifier.fillMaxSize()) {
        gameBoard(gameViewModel)
        gameInfoPanel(gameViewModel, focusRequester)
    }
}

@Composable
private fun gameBoard(gameViewModel: GameViewModel) {
    Canvas(
        modifier = Modifier.size(
            (GRID_COLS * GRID_CELL_SIZE_DP).dp,
            (GRID_ROWS * GRID_CELL_SIZE_DP).dp
        )
    ) {
        drawGrid()
        drawSnake(gameViewModel.snake)
        drawFood(gameViewModel.food.value)
    }
}

@Composable
private fun gameInfoPanel(gameViewModel: GameViewModel, focusRequester: FocusRequester) {
    Column(
        modifier = Modifier
            .width(UI_PANEL_WIDTH_DP.dp)
            .height((GRID_ROWS * GRID_CELL_SIZE_DP).dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            gameViewModel.restartGame()
            focusRequester.requestFocus()
        }) { Text("Restart") }

        Spacer(modifier = Modifier.height(12.dp))
        Text("Pause: ${if (gameViewModel.isPaused.value) "On" else "Off"}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Length: ${gameViewModel.snake.size}", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Speed: ${gameViewModel.speed.value} cells/s", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(12.dp))

        if (gameViewModel.gameOver.value) {
            Text("Game Over", fontSize = 24.sp, color = Color.Red)
        }
    }
}
