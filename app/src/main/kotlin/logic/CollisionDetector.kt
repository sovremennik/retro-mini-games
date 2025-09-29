package logic

import androidx.compose.ui.geometry.Offset
import model.GRID_COLS
import model.GRID_ROWS

interface CollisionDetector {
    fun checkWallCollision(position: Offset): Boolean
    fun checkSelfCollision(snake: List<Offset>): Boolean
    fun checkCollision(snake: List<Offset>, newHead: Offset): Boolean
}

class DefaultCollisionDetector : CollisionDetector {
    override fun checkWallCollision(position: Offset): Boolean {
        return position.x < 0 || position.x >= GRID_COLS ||
                position.y < 0 || position.y >= GRID_ROWS
    }

    override fun checkSelfCollision(snake: List<Offset>): Boolean {
        val head = snake.first()
        return snake.drop(1).contains(head)
    }

    override fun checkCollision(snake: List<Offset>, newHead: Offset): Boolean {
        return checkWallCollision(newHead) || snake.contains(newHead)
    }
}
