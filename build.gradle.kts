plugins {
    alias(libs.plugins.spotless)
}

repositories {
    mavenCentral()
}

spotless {
    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint()
    }
}
