plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id(Plugins.kotlin) version Versions.kotlin
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(platform(Dependencies.kotlinBom))
    implementation(Dependencies.kotlinStdlib)

    // Logging
    implementation(Dependencies.logbackClassic)
    implementation(Dependencies.kotlinLogging)

    // Util
    implementation(Dependencies.reflections)

    testImplementation(TestDependencies.kotlinTest)
    testImplementation(TestDependencies.kotlinTestJUnit)
}

application {
    mainClass.set("com.jaketschwartz.adventofcode.MainClassKt")
}
