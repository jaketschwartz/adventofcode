package com.jaketschwartz.adventofcode.extensions

import kotlin.reflect.KClass

fun <T: Any> T?.checkNotNull(lazyMessage: () -> String): T {
    checkNotNull(this, lazyMessage)
    return this
}

fun <T: Any> KClass<T>.qualifiedNameNotNull(): String = this.java.packageName
    .checkNotNull { "Expected the qualif" }

fun <T: Any> T?.ifNull(block: () -> Unit): T? = this.also { if (this == null) block() }
