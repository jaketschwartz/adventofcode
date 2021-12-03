package com.jaketschwartz.adventofcode.extensions

import kotlin.reflect.KClass

fun <T: Any> T?.checkNotNull(lazyMessage: () -> String): T {
    checkNotNull(this, lazyMessage)
    return this
}

fun <T: Any> KClass<T>.qualifiedNameNotNull(): String = this.java.packageName
    .checkNotNull { "Expected the qualif" }
