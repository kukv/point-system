import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("jvm") apply false
    id("com.google.devtools.ksp")
}

dependencies {
    implementation("io.insert-koin:koin-ktor:3.5.1")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.1")

    compileOnly("io.insert-koin:koin-annotations:1.3.0")
    ksp("io.insert-koin:koin-ksp-compiler:1.3.0")

    testImplementation("io.insert-koin:koin-test:3.5.0")
    testImplementation("io.insert-koin:koin-test-junit5:3.5.0")
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
    sourceSets.test {
        kotlin.srcDir("build/generated/ksp/test/kotlin")
    }
}
