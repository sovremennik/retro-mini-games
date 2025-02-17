package logic

import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset
import model.GRID_COLS
import model.GRID_ROWS

enum class Direction { UP, DOWN, LEFT, RIGHT }

fun isOppositeDirection(d1: Direction, d2: Direction): Boolean {
    return (d1 == Direction.UP && d2 == Direction.DOWN) ||
            (d1 == Direction.DOWN && d2 == Direction.UP) ||
            (d1 == Direction.LEFT && d2 == Direction.RIGHT) ||
            (d1 == Direction.RIGHT && d2 == Direction.LEFT)
}

fun moveSnake(snake: MutableList<Offset>, direction: Direction, onGameOver: () -> Unit) {
    val head = snake.first()
    val newHead = when (direction) {
        Direction.UP -> Offset(head.x, head.y - 1f)
        Direction.DOWN -> Offset(head.x, head.y + 1f)
        Direction.LEFT -> Offset(head.x - 1f, head.y)
        Direction.RIGHT -> Offset(head.x + 1f, head.y)
    }
    if (newHead.x < 0 || newHead.x >= GRID_COLS || newHead.y < 0 || newHead.y >= GRID_ROWS || snake.contains(
            newHead
        )
    ) {
        onGameOver()
        return
    }
    snake.add(0, newHead)
    snake.removeLast()
}

fun checkFoodCollision(snake: MutableList<Offset>, food: MutableState<Offset>) {
    val head = snake.first()
    if (head == food.value) {
        snake.add(snake.last())
        food.value = randomFoodPosition(snake)
    }
}

fun randomFoodPosition(occupied: List<Offset>): Offset {
    val available = mutableListOf<Offset>()
    for (x in 0 until GRID_COLS) {
        for (y in 0 until GRID_ROWS) {
            val pos = Offset(x.toFloat(), y.toFloat())
            if (pos !in occupied) available.add(pos)
        }
    }
    return if (available.isNotEmpty()) available.random() else Offset.Zero
}
