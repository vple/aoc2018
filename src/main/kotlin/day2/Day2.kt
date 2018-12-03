package day2

import util.loadResource

/**
 * Solutions for [Advent of Code Day 2](https://adventofcode.com/2018/day/2).
 */

const val INPUT_FILE = "/day2/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val boxIds = input.lines().filterNot { it.isEmpty() }

    println("Part 1")
    val checksum = computeChecksum(boxIds)
    println(checksum)
}

/**
 * Computes a checksum for the given [boxIds].
 */
fun computeChecksum(boxIds: List<String>): Int {
    val twoLetterBoxCount = boxIds.filter { it.containsExactly2() }.count()
    val threeLetterBoxCount = boxIds.filter { it.containsExactly3() }.count()
    return twoLetterBoxCount * threeLetterBoxCount
}

/**
 * Counts the number of times each letter occurs in the given [boxId].
 */
fun countLetters(boxId: String): Map<Char, Int> {
    return boxId.groupingBy { it }.eachCount()
}

/**
 * Returns true if this string contains exactly [n] of any letter.
 */
fun String.containsExactlyN(n: Int): Boolean {
    return countLetters(this).values.contains(n)
}

/**
 * Returns true if this string contains exactly 2 of any letter.
 */
fun String.containsExactly2() = this.containsExactlyN(2)

/**
 * Returns true if this string contains exactly 3 of any letter.
 */
fun String.containsExactly3() = this.containsExactlyN(3)