package di

import logic.CollisionDetector
import logic.DefaultCollisionDetector
import logic.DefaultFoodGenerator
import logic.DefaultSnakeMovement
import logic.FoodGenerator
import logic.SnakeMovement


object GameDependencies {

    val snakeMovement: SnakeMovement by lazy { DefaultSnakeMovement() }
    val collisionDetector: CollisionDetector by lazy { DefaultCollisionDetector() }
    val foodGenerator: FoodGenerator by lazy { DefaultFoodGenerator() }

    fun createGameViewModel() = viewmodel.GameViewModel(
        snakeMovement = snakeMovement,
        collisionDetector = collisionDetector,
        foodGenerator = foodGenerator
    )
}
