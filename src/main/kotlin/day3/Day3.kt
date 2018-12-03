package day3

import util.loadResource

/**
 * Solutions for [Advent of Code Day 3](https://adventofcode.com/2018/day/3).
 */

const val INPUT_FILE = "/day3/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val rawClaims = input.lines().filterNot { it.isEmpty() }
    val claims = rawClaims.map { Claim.parse(it) }

    println("Part 1")
    val overlapArea = computeOverlapArea(claims)
    println(overlapArea)

    println("Part 2")
    val nonOverlappingClaims = findNonOverlappingClaims(claims)
    println(nonOverlappingClaims)
}

/**
 * Determines how many times each square inch of fabric has been claimed in the given [claims].
 */
fun trackClaimedFabric(claims: List<Claim>): Map<Coordinate, Int> {
    return claims
        .map { it.getAllCoordinates() }.flatten()
        .groupingBy { it }
        .eachCount();
}

/**
 * Determines how much fabric is within multiple [claims].
 */
fun computeOverlapArea(claims: List<Claim>): Int = findFabricWithinMultipleClaims(claims).count()

/**
 * Finds the coordinates for all fabric within multiple [claims].
 */
fun findFabricWithinMultipleClaims(claims: List<Claim>) = trackClaimedFabric(claims).filter { it.value >= 2 }.keys

/**
 * Finds all claims in the given [claims] that don't overlap with any other claims.
 */
fun findNonOverlappingClaims(claims: List<Claim>): List<Claim> {
    val overlapCoordinates = findFabricWithinMultipleClaims(claims)
    return claims.filter { it.getAllCoordinates().intersect(overlapCoordinates).isEmpty() }
}