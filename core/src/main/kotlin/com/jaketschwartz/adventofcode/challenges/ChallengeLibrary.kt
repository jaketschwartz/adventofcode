package com.jaketschwartz.adventofcode.challenges

import com.jaketschwartz.adventofcode.challenges.c2021.Challenge202101
import com.jaketschwartz.adventofcode.challenges.c2021.Challenge202102
import com.jaketschwartz.adventofcode.extensions.padSingleDigit

object ChallengeLibrary {
    // TODO: maybe scan the classpath for all challenges that are coded and just automagically add them.  Maybe with annotation scanning?
    private val challengeMap: Map<ChallengeKey, Challenge> = mapOf(
        2021 day 1 to Challenge202101(),
        2021 day 2 to Challenge202102(),
    )

    fun executeChallenge(year: Int, day: Int, executionType: ChallengeExecutionType) {
        val challenge = challengeMap[ChallengeKey(year, day)]
            ?: throw IllegalStateException("The challenge for $year/$${day.padSingleDigit()} has not yet been coded")

        val firstResult = { println("Result for the first challenge for ${challenge.challengeName}: ${challenge.partOne(challenge.parser.lines)}") }
        val secondResult = { println("Result for the second challenge for ${challenge.challengeName}: ${challenge.partTwo(challenge.parser.lines)}") }
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

private infix fun Int.day(day: Int): ChallengeKey = ChallengeKey(year = this, day = day)
