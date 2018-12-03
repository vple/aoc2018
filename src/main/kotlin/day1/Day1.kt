package day1

import util.loadResource
import java.lang.RuntimeException

/**
 * Solutions for [Advent of Code Day 1](https://adventofcode.com/2018/day/1).
 */

const val INPUT_FILE = "/day1/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val frequenciesChanges = input.lines().filterNot { it.isEmpty() }.map { it.toInt() }

    println("Part 1")
    val frequency = applyFrequencyChanges(0, frequenciesChanges)
    println(frequency)

    println("Part 2")
    val repeatedFrequency = findRepeatedFrequency(0, frequenciesChanges)
    println(repeatedFrequency)
}

/**
 * Applies the given [frequenciesChanges] to the given [startingFrequency], returning the result.
 */
fun applyFrequencyChanges(startingFrequency: Int, frequenciesChanges: List<Int>): Int {
    return startingFrequency + frequenciesChanges.sum()
}

/**
 * Repeatedly applies the given [frequenciesChanges] to the given [startingFrequency], returning the first frequency found twice.
 */
fun findRepeatedFrequency(startingFrequency: Int, frequenciesChanges: List<Int>): Int {
    val infiniteFrequencyChanges = generateSequence { frequenciesChanges.asSequence() }.flatten()

    var currentFrequency = startingFrequency
    val seenFrequencies = mutableSetOf<Int>()
    seenFrequencies.add(currentFrequency)
    for (change in infiniteFrequencyChanges) {
        currentFrequency += change
        if (seenFrequencies.contains(currentFrequency)) {
            return currentFrequency
        } else {
            seenFrequencies.add(currentFrequency)
        }
    }
    throw RuntimeException()
}