import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import model.GRID_CELL_SIZE_DP
import model.GRID_COLS
import model.GRID_ROWS
import model.UI_PANEL_WIDTH_DP
import model.UI_TOP_BAR_HEIGHT_DP
import ui.snakeGame
import util.logException


fun main() = application {
    System.setProperty("skiko.renderApi", "OPENGL")

    Thread.setDefaultUncaughtExceptionHandler { thread, exception ->
        logException(thread, exception)
    }

    val windowWidth = ((GRID_COLS * GRID_CELL_SIZE_DP) + UI_PANEL_WIDTH_DP).dp
    val windowHeight = ((GRID_ROWS * GRID_CELL_SIZE_DP) + UI_TOP_BAR_HEIGHT_DP).dp

    Window(
        onCloseRequest = ::exitApplication,
        title = "Snake",
        state = rememberWindowState(size = DpSize(windowWidth, windowHeight))
    ) {
        MaterialTheme {
            snakeGame()
        }
    }
}
