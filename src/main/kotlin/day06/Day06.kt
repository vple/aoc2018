package day06

import util.loadResource

/**
 * [Advent of Code 2017 Day 6](https://adventofcode.com/2017/day/6)
 */

const val INPUT_FILE = "/day06/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val regex = Regex("(\\d+), (\\d+)")
    val coordinates =
        input.lines().filterNot { it.isEmpty() }
            .map { regex.matchEntire(it)!!.groupValues }
            .map { Coordinate(it[1].toInt(), it[2].toInt()) }

    println("Part 1")
    println(search(coordinates, ::findUniqueClosestCoordinate, ::mostFrequentBoundedCoordinate))

    println("Part 2")
    println(search(coordinates, ::computeTotalDistance, withinTotalDistance(10000)))
}

/**
 * Searches for and returns the area of the "best" region.
 *
 * This method will [score] all coordinates within a bounding box around the given [coordinates].
 * It will then [createBestScoreFilter], using that filter to determine which scores correspond to the best region.
 */
fun <T> search(
    coordinates: List<Coordinate>,
    score: (Coordinate, List<Coordinate>) -> T?,
    createBestScoreFilter: (scores: Map<Coordinate, T>, bounds: Pair<Coordinate, Coordinate>) -> (T) -> Boolean
): Int {
    val bounds = computeBounds(coordinates)
    val allCoordinates = computeAllCoordinates(bounds)
    val scores =
        allCoordinates.mapNotNull { coordinate ->
            score(coordinate, coordinates)?.let {
                Pair(coordinate, it)
            }
        }.toMap()
    val bestScoreFilter = createBestScoreFilter(scores, bounds)
    return scores.count { bestScoreFilter(it.value) }
}

/**
 * Given a [target], returns the unique closest coordinate in [coordinates]. Returns null if there is no unique closest coordinate.
 */
fun findUniqueClosestCoordinate(target: Coordinate, coordinates: List<Coordinate>): Coordinate? {
    val distances = coordinates.associate { Pair(it, it.distance(target)) }
    val minDistance = distances.values.min()!!
    return distances.filterValues { it == minDistance }.keys.singleOrNull()
}

/**
 * Returns a filter that matches to the coordinate occurring most frequently as a [scores] value.
 * Excludes all coordinate scores that are along the boundary of the scores (i.e. the corresponding key is on the boundary).
 */
fun mostFrequentBoundedCoordinate(
    scores: Map<Coordinate, Coordinate>,
    bounds: Pair<Coordinate, Coordinate>
): (Coordinate) -> Boolean {
    val unbounded = scores.filterKeys { onBoundary(it, bounds) }.keys
    val frequency = scores.values.groupingBy { it }.eachCount()
    val mostFrequent = frequency.filterKeys { !unbounded.contains(it) }.maxBy { it.value }!!.key
    return { coordinate -> mostFrequent.equals(coordinate) }
}

/**
 * Given a [target], returns the sum of all distances to each coordinate in [coordinates].
 */
fun computeTotalDistance(target: Coordinate, coordinates: List<Coordinate>): Int {
    return coordinates.map { it.distance(target) }.sum()
}

/**
 * Returns a function that will create a best score filter that filters for distances less than [maxDistance].
 */
fun withinTotalDistance(maxDistance: Int): (Map<Coordinate, Int>, Pair<Coordinate, Coordinate>) -> (Int) -> Boolean {
    val distanceFilter = { distance: Int -> distance < maxDistance }
    return { _, _ -> distanceFilter }
}

/**
 * Determines the bounds for the given [coordinates].
 */
fun computeBounds(coordinates: List<Coordinate>): Pair<Coordinate, Coordinate> {
    val xs = coordinates.map { it.x }
    val ys = coordinates.map { it.y }

    val topLeft = Coordinate(xs.min()!!, ys.min()!!)
    val bottomRight = Coordinate(xs.max()!!, ys.max()!!)

    return Pair(topLeft, bottomRight)
}

/**
 * Returns all coordinates within the given [bounds].
 */
fun computeAllCoordinates(bounds: Pair<Coordinate, Coordinate>): List<Coordinate> {
    return (bounds.first.x..bounds.second.x).map { x ->
        (bounds.first.y..bounds.second.y).map { y ->
            Coordinate(x, y)
        }
    }.flatten()
}

/**
 * Returns true if the given [coordinate] is within the [bounds].
 */
fun inBounds(coordinate: Coordinate, bounds: Pair<Coordinate, Coordinate>): Boolean {
    return coordinate.x >= bounds.first.x
        && coordinate.y >= bounds.first.y
        && coordinate.x <= bounds.second.x
        && coordinate.y <= bounds.second.y
}

/**
 * Returns true if the given [coordinate] lies on the boundary specified by [bounds].
 */
fun onBoundary(coordinate: Coordinate, bounds: Pair<Coordinate, Coordinate>): Boolean {
    return coordinate.x == bounds.first.x
        || coordinate.y == bounds.first.y
        || coordinate.x == bounds.second.x
        || coordinate.y == bounds.second.y
}