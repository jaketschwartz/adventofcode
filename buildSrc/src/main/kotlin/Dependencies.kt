object Plugins {
    const val kotlin = "org.jetbrains.kotlin.jvm"
}

object Dependencies {
    // Kotlin
    const val kotlinBom = "org.jetbrains.kotlin:kotlin-bom:${Versions.kotlin}"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    // Logging
    const val kotlinLogging = "io.github.microutils:kotlin-logging-jvm:${Versions.kotlinLogging}"
    const val logbackClassic = "ch.qos.logback:logback-classic:${Versions.logback}"

    // Util
    const val reflections = "org.reflections:reflections:${Versions.reflections}"
}

object TestDependencies {
    // Kotlin
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    const val kotlinTestJUnit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
}
