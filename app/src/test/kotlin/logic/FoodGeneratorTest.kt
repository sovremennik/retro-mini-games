package logic

import androidx.compose.ui.geometry.Offset
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FoodGeneratorTest {

    private val generator = DefaultFoodGenerator()

    @Test
    fun `generateFood returns position not occupied by snake`() {
        val snake = listOf(
            Offset(0f, 0f),
            Offset(1f, 0f),
            Offset(2f, 0f)
        )
        val food = generator.generateFood(snake)

        assertFalse(snake.contains(food))
    }

    @Test
    fun `generateFood returns position within grid boundaries`() {
        val snake = listOf(Offset(5f, 5f))
        val food = generator.generateFood(snake)

        assertTrue(food.x >= 0f && food.x < 30f)
        assertTrue(food.y >= 0f && food.y < 20f)
    }

    @Test
    fun `generateFood returns different positions on multiple calls`() {
        val snake = listOf(Offset(5f, 5f))
        val positions = mutableSetOf<Offset>()

        repeat(10) {
            positions.add(generator.generateFood(snake))
        }

        assertTrue(positions.size > 1)
    }

    @Test
    fun `generateFood returns valid position when most of grid is occupied`() {
        val snake = mutableListOf<Offset>()
        for (x in 0 until 30) {
            for (y in 0 until 19) {
                snake.add(Offset(x.toFloat(), y.toFloat()))
            }
        }

        val food = generator.generateFood(snake)

        assertFalse(snake.contains(food))
        assertTrue(food.x >= 0f && food.x < 30f)
        assertTrue(food.y >= 0f && food.y < 20f)
    }
}
