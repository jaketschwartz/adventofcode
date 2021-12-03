plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id(Plugins.kotlin) version Versions.kotlin
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform(Dependencies.kotlinBom))
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.reflections)

    testImplementation(TestDependencies.kotlinTest)
    testImplementation(TestDependencies.kotlinTestJUnit)
}

application {
    mainClass.set("com.jaketschwartz.adventofcode.MainClassKt")
}
