plugins {
    `kotlin-dsl`
    id("com.diffplug.spotless") version "6.22.0"
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20")
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.9.20")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.9.20")

    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.1.5")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.3")

    implementation("org.springdoc:springdoc-openapi-gradle-plugin:1.6.0")

    implementation("org.dddjava.jig:jig-gradle-plugin:2023.10.1")

    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.22.0")

    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.20-1.0.14")
}

spotless {
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude("**/build/**/*.kts")

        ktlint()
    }
}
