package day07

import util.loadResource

/**
 * [Advent of Code 2017 Day 7](https://adventofcode.com/2017/day/7)
 */

const val INPUT_FILE = "/day07/input.txt"

fun main(args: Array<String>) {
    val input = loadResource(INPUT_FILE)
    val lines = input.lines().filterNot { it.isEmpty() }
    val regex = Regex("Step (\\w) must be finished before step (\\w) can begin.")
    val dependencies = lines
        .mapNotNull { regex.matchEntire(it)?.groupValues }
        .map { Pair(it[1].first(), it[2].first()) }

    println("Part 1")
    val singleWorkerSchedule = schedule(dependencies, 1)
    val steps = singleWorkerSchedule.toList().sortedBy { (step, start) -> start }.map { (step, _) -> step }
    println(steps.joinToString(separator = ""))

    println("Part 2")
    val fiveWorkerSchedule = schedule(dependencies, 5, ::stepTime)
    val completionTime = fiveWorkerSchedule.map { (step, start) -> start + stepTime(step) }.max()
    println(completionTime)
}

/**
 * Schedules the steps in the given [dependencies], using [numWorkers] workers and with each step taking the amount of time given by [stepTime].
 * Returns the start times for each step.
 */
fun schedule(
    dependencies: List<Pair<Char, Char>>,
    numWorkers: Int = 1,
    stepTime: (Char) -> Int = { 1 }
): Map<Char, Int> {
    return scheduleHelper(
        dependencies = dependencies,
        allSteps = dependencies.map { listOf(it.first, it.second) }.flatten().toSet(),
        numWorkers = numWorkers,
        stepTime = stepTime,
        currentTime = 0)
}

/**
 * Recursive helper function to sequence the [dependencies].
 */
fun scheduleHelper(
    sequence: Map<Char, Int> = mapOf(), // Step to start time
    dependencies: List<Pair<Char, Char>>,
    allSteps: Set<Char>,
    numWorkers: Int,
    stepTime: (Char) -> Int,
    currentTime: Int = 0
): Map<Char, Int> {
    val completedSteps = sequence.filter { (step, start) -> start + stepTime(step) <= currentTime }.keys
    val currentSteps = sequence.filterKeys { !completedSteps.contains(it) }.keys
    val remainingSteps = allSteps - completedSteps - currentSteps

    if (remainingSteps.isEmpty()) {
        return sequence
    }

    val remainingDependencies = dependencies.filterNot { (step, _) -> completedSteps.contains(step) }
    val availableSteps = remainingSteps - remainingDependencies.map { it.second }
    val availableWorkers = numWorkers - currentSteps.size
    val steps = availableSteps.sorted().take(availableWorkers)
    val nextSequence = sequence + steps.map { Pair(it, currentTime)}

    val nextCompletedStepTime = nextSequence
        .filterKeys { currentSteps.contains(it) || steps.contains(it) }
        .map { (step, start) -> start + stepTime(step) }
        .min()!!

    return scheduleHelper(nextSequence, dependencies, allSteps, numWorkers, stepTime, nextCompletedStepTime)
}

/**
 * The amount of time it takes to complete [step].
 */
fun stepTime(step: Char) = step - 'A' + 60 + 1