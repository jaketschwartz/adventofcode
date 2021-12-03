package com.jaketschwartz.adventofcode.util

import com.jaketschwartz.adventofcode.extensions.ifNull
import mu.KLogging
import org.reflections.Reflections

/**
 * Helps scan the classpath for various classes.  Useful for home-grown app boot functionalities.
 */
object AdventTypeLoader : KLogging() {
    /**
     * Finds all classes that inherit from the given implementation of ArgumentlessConstructorPromise
     * and instantiates them into a neat little List.
     */
    inline fun <reified T: ArgumentlessConstructorPromise> initializeAllImplementationsOfType(): List<T> = Reflections(T::class.java.packageName)
        .getSubTypesOf(T::class.java)
        .mapNotNull { clazz ->
            try {
                (clazz.declaredConstructors.singleOrNull()?.newInstance() as? T)
                    .ifNull { logger.error("Failed to find a single constructor for class [${clazz.name}]") }
            } catch (e: Exception) {
                logger.error(e) { "Failed to instantiate a new instance of [${clazz.name}]" }
                null
            }
        }
}
