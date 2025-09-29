package logic

import androidx.compose.ui.geometry.Offset
import model.GRID_COLS
import model.GRID_ROWS

interface FoodGenerator {
    fun generateFood(occupiedPositions: List<Offset>): Offset
}

class DefaultFoodGenerator : FoodGenerator {
    override fun generateFood(occupiedPositions: List<Offset>): Offset {
        val available = mutableListOf<Offset>()
        for (x in 0 until GRID_COLS) {
            for (y in 0 until GRID_ROWS) {
                val pos = Offset(x.toFloat(), y.toFloat())
                if (pos !in occupiedPositions) available.add(pos)
            }
        }
        return if (available.isNotEmpty()) available.random() else Offset.Zero
    }
}
