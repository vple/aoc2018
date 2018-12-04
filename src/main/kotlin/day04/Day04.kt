package day04

import util.loadResource

/**
 * [Advent of Code 2017 Day 4](https://adventofcode.com/2017/day/4)
 */

const val INPUT_FILE = "/day04/input.txt"
const val GUARD_ID_PATTERN = "Guard #(\\d+) begins shift"
val GUARD_ID_REGEX = Regex(GUARD_ID_PATTERN)


fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val records = input.lines().filterNot { it.isEmpty() }
    val guards = parseRecords(records)

    println("Part 1")
    val strategy1Guard = strategy1(guards)
    println(strategy1Guard.id * strategy1Guard.mostAsleepAt.first)

    println("Part 2")
    val strategy2Guard = strategy2(guards)
    println(strategy2Guard.id * strategy2Guard.mostAsleepAt.first)
}

/**
 * Parses the given [records] to determine which guards are on duty and when / how often they are asleep.
 */
fun parseRecords(records: List<String>): List<Guard> {
    // Since records start with a timestamp, the natural sort order results in a chronological sort.
    val chronologicalRecords = records.sorted()

    val logsByGuard = mutableMapOf<Int, MutableList<String>>()
    var guardId = -1
    for (record in chronologicalRecords) {
        val matchResult = GUARD_ID_REGEX.find(record)
        if (matchResult != null) {
            // A guard began their shift.
            guardId = matchResult.groupValues[1].toInt()
        } else {
            // The guard on duty did something.
            logsByGuard.getOrPut(guardId) { mutableListOf() }.add(record)
        }
    }

    return logsByGuard.map { Guard.parseRecords(it.key, it.value) }
}

/**
 * Of the given [guards], returns the one that is asleep the most.
 */
fun strategy1(guards: List<Guard>) = guards.maxBy { it.totalTimeAsleep }!!

/**
 * Of the given [guards], returns the one that is most frequently asleep at the same minute.
 */
fun strategy2(guards: List<Guard>) = guards.maxBy { it.mostAsleepAt.second }!!