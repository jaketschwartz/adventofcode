package com.jaketschwartz.adventofcode.testhelper

import kotlin.test.fail

object AdventTestHelpers {
    fun <T : Any> assertSucceeds(
        message: String = "Failed to run block successfully",
        action: () -> T,
    ): T = try {
        action()
    } catch (e: Exception) {
        fail(message, e)
    }
}