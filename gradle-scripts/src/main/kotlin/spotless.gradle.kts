plugins {
    id("com.diffplug.spotless")
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
