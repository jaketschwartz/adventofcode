object Plugins {
    const val kotlin = "org.jetbrains.kotlin.jvm"
}

object Dependencies {
    // Kotlin
    const val kotlinBom = "org.jetbrains.kotlin:kotlin-bom:${Versions.kotlin}"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    // External
    const val reflections = "org.reflections:reflections:${Versions.reflections}"
}

object TestDependencies {
    // Kotlin
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    const val kotlinTestJUnit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
}
