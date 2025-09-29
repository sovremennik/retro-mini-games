package logic

enum class Direction { UP, DOWN, LEFT, RIGHT }

fun isOppositeDirection(d1: Direction, d2: Direction): Boolean {
    return (d1 == Direction.UP && d2 == Direction.DOWN) ||
            (d1 == Direction.DOWN && d2 == Direction.UP) ||
            (d1 == Direction.LEFT && d2 == Direction.RIGHT) ||
            (d1 == Direction.RIGHT && d2 == Direction.LEFT)
}
