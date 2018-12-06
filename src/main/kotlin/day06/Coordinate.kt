package day06

import kotlin.math.abs

/**
 * Represents a square inch on the piece of fabric.
 *
 * Coordinates are measured from the top-left corner.
 */
data class Coordinate(val x: Int, val y: Int) {
    val neighbors: List<Coordinate> by lazy {
        listOf(
            Coordinate(x+1, y),
            Coordinate(x, y+1),
            Coordinate(x-1, y),
            Coordinate(x, y-1)
        )

//        val neighbors = mutableListOf<Coordinate>()
//        for (i in -1..1) {
//            for (j in -1..1) {
//                if (i != 0 || j != 0) {
//                    neighbors.add(Coordinate(x + i, y + j))
//                }
//            }
//        }
//        neighbors.toList()
    }

    fun distance(other: Coordinate): Int {
        return abs(x - other.x) + abs(y - other.y)
    }
}