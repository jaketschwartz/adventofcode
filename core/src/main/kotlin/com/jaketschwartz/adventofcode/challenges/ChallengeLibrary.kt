package com.jaketschwartz.adventofcode.challenges

import com.jaketschwartz.adventofcode.extensions.padSingleDigit

object ChallengeLibrary {
    // TODO: maybe scan the classpath for all challenges that are coded and just automagically add them.  Maybe with annotation scanning?
    private val challengeMap: Map<ChallengeKey, Challenge<*, *>> = mapOf()

    fun executeChallenge(year: Int, day: Int, executionType: ChallengeExecutionType) {
        val challenge = challengeMap[ChallengeKey(year, day)]
            ?: throw IllegalStateException("The challenge for $year/$${day.padSingleDigit()} has not yet been coded")

        val firstResult = { println("Result for the first challenge for ${challenge.challengeName}: ${challenge.partOne(challenge.parser.getPartOneLines())}") }
        val secondResult = { println("Result for the second challenge for ${challenge.challengeName}: ${challenge.partTwo(challenge.parser.getPartTwoLines())}") }
        when (executionType) {
            ChallengeExecutionType.FIRST -> firstResult()
            ChallengeExecutionType.SECOND -> secondResult()
            ChallengeExecutionType.BOTH -> firstResult().also { secondResult() }
        }
    }
}

data class ChallengeKey(val year: Int, val day: Int)

enum class ChallengeExecutionType {
    FIRST,
    SECOND,
    BOTH
}
