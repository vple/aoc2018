package day05

import util.loadResource

/**
 * [Advent of Code 2017 Day 5](https://adventofcode.com/2017/day/5)
 */

const val INPUT_FILE = "/day05/input.txt"
const val CAPITALIZATION_DIFF = 'a' - 'A'

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val polymer = input.lines().first()

    println("Part 1")
    println(polymer.react().count())

    println("Part 2")
    println(findShortestReactedPolymerLength(polymer))
}

/**
 * Finds the length of the shortest polymer formed by removing all instances of one unit and reacting the result.
 */
fun findShortestReactedPolymerLength(polymer: String): Int {
    // This doesn't change the final result, but reduces the number of reactions when finding the shortest polymer.
    val reactedPolymer = polymer.react()
    return ('A'..'Z')
        .map { reactedPolymer.removeUnit(it) }
        .map { it.react() }
        .map { it.count() }
        .min()!!
}

/**
 * Returns the polymer formed by removing all instances of the given [unit] from this polymer.
 */
fun String.removeUnit(unit: Char): String = filterNot { areSameType(it, unit) }

/**
 * Reacts a polymer, removing any two consecutive units that are opposite polarity.
 */
fun String.react(): String {
    return fold("") { acc, c ->
        val lastChar = acc.lastOrNull()
        when {
            lastChar == null -> c.toString()
            areOppositePolarity(lastChar, c) -> acc.dropLast(1)
            else -> acc.plus(c)
        }
    }
}

/**
 * Returns true if [x] and [y] are the same type (letter).
 */
fun areSameType(x: Char, y: Char): Boolean {
    val sameType = (x - y) % CAPITALIZATION_DIFF == 0
    return x.isLetter() && y.isLetter() && sameType
}

/**
 * Returns true if [x] and [y] are the same type but have opposite polarity.
 */
fun areOppositePolarity(x: Char, y: Char): Boolean {
    return areSameType(x, y) && x != y
}