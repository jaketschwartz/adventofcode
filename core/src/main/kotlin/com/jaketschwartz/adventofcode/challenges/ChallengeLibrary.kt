package com.jaketschwartz.adventofcode.challenges

import com.jaketschwartz.adventofcode.extensions.padSingleDigit
import org.reflections.Reflections

object ChallengeLibrary {
    // TODO: maybe scan the classpath for all challenges that are coded and just automagically add them.  Maybe with annotation scanning?
    private val challengeMap: Map<ChallengeKey, Challenge> by lazy {
        Reflections(Challenge::class.java.packageName)
            .getSubTypesOf(Challenge::class.java)
            .mapNotNull { clazz ->
                try {
                    clazz.declaredConstructors.singleOrNull()?.newInstance() as? Challenge
                } catch(e: Exception) {
                    println("Failed to instantiate a new instance of [${clazz.simpleName}]")
                    null
                }?.also {
                    println("Successfully loaded challenge [${it::class.qualifiedName} / ${it.challengeName}]")
                }
            }
            .associateBy { challenge -> ChallengeKey(year = challenge.year, day = challenge.day) }
    }

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
