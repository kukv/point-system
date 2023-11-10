plugins {
    id("com.diffplug.spotless") version "6.22.0"
}

repositories {
    mavenCentral()
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**/*.kt", "**/bin/**/*.kt")

        ktlint()
    }

    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude("**/build/**/*.kts")

        ktlint()
    }
}
