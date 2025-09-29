package logic

import androidx.compose.ui.geometry.Offset

interface SnakeMovement {
    fun calculateNewHead(currentHead: Offset, direction: Direction): Offset
    fun moveSnake(snake: List<Offset>, newHead: Offset): List<Offset>
}

class DefaultSnakeMovement : SnakeMovement {
    override fun calculateNewHead(currentHead: Offset, direction: Direction): Offset {
        return when (direction) {
            Direction.UP -> Offset(currentHead.x, currentHead.y - 1f)
            Direction.DOWN -> Offset(currentHead.x, currentHead.y + 1f)
            Direction.LEFT -> Offset(currentHead.x - 1f, currentHead.y)
            Direction.RIGHT -> Offset(currentHead.x + 1f, currentHead.y)
        }
    }

    override fun moveSnake(snake: List<Offset>, newHead: Offset): List<Offset> {
        val newSnake = mutableListOf(newHead)
        newSnake.addAll(snake.dropLast(1))
        return newSnake
    }
}
