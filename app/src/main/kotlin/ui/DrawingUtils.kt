package ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import model.COLOR_FOOD
import model.COLOR_GRID
import model.COLOR_SNAKE
import model.GRID_CELL_SIZE_DP
import model.GRID_COLS
import model.GRID_ROWS

fun DrawScope.drawGrid() {
    for (row in 0..GRID_ROWS) {
        drawLine(
            color = COLOR_GRID,
            start = Offset(0f, row * GRID_CELL_SIZE_DP.toFloat()),
            end = Offset(size.width, row * GRID_CELL_SIZE_DP.toFloat())
        )
    }
    for (col in 0..GRID_COLS) {
        drawLine(
            color = COLOR_GRID,
            start = Offset(col * GRID_CELL_SIZE_DP.toFloat(), 0f),
            end = Offset(col * GRID_CELL_SIZE_DP.toFloat(), size.height)
        )
    }
}

fun DrawScope.drawSnake(snake: List<Offset>) {
    if (snake.isNotEmpty()) {
        val head = snake.first()
        drawRect(
            color = COLOR_SNAKE,
            topLeft = Offset(
                head.x * GRID_CELL_SIZE_DP.toFloat(),
                head.y * GRID_CELL_SIZE_DP.toFloat()
            ),
            size = Size(GRID_CELL_SIZE_DP.toFloat(), GRID_CELL_SIZE_DP.toFloat())
        )
        for (i in 1 until snake.size) {
            val segment = snake[i]
            drawRect(
                color = COLOR_SNAKE,
                topLeft = Offset(
                    segment.x * GRID_CELL_SIZE_DP.toFloat(),
                    segment.y * GRID_CELL_SIZE_DP.toFloat()
                ),
                size = Size(GRID_CELL_SIZE_DP.toFloat(), GRID_CELL_SIZE_DP.toFloat())
            )
        }
    }
}

fun DrawScope.drawFood(foodPos: Offset) {
    drawRect(
        color = COLOR_FOOD,
        topLeft = Offset(
            foodPos.x * GRID_CELL_SIZE_DP.toFloat(),
            foodPos.y * GRID_CELL_SIZE_DP.toFloat()
        ),
        size = Size(GRID_CELL_SIZE_DP.toFloat(), GRID_CELL_SIZE_DP.toFloat())
    )
}
