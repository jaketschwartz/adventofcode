package com.jaketschwartz.adventofcode.challenges.c2021

import com.jaketschwartz.adventofcode.challenges.Challenge
import com.jaketschwartz.adventofcode.extensions.checkNotNull
import kotlin.math.absoluteValue

class Challenge202107 : Challenge {
    override val day: Int = 7
    override val year: Int = 2021
    override val challengeName: String = "The Treachery of Whales"

    override fun partOne(lines: List<String>) = lines
        .single()
        .split(",")
        .map { it.toInt() }
        .let { values ->
            val upperBound = values.maxOrNull().checkNotNull { "List is empty" }
            val lowerBound = values.minOrNull().checkNotNull { "List is empty" }
            (lowerBound..upperBound).minOf { bound ->
                values.sumOf { bound.minus(it).absoluteValue }
            }
        }

    override fun partTwo(lines: List<String>) = lines
        .single()
        .split(",")
        .map { it.toInt() }
        .let { values ->
            val upperBound = values.maxOrNull().checkNotNull { "List is empty" }
            val lowerBound = values.minOrNull().checkNotNull { "List is empty" }
            (lowerBound..upperBound).minOf { bound ->
                values.sumOf { (1..bound.minus(it).absoluteValue).sum() }
            }
        }
}