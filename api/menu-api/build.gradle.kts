import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.springdoc.openapi)
    alias(libs.plugins.spotless)
}

repositories {
    mavenCentral()
}

dependencies {

    implementation(libs.bundles.spring.api.starter)
    implementation(libs.bundles.exposed.spring.stater)

    runtimeOnly(libs.postgresql.jdbc)

    annotationProcessor(libs.spring.boot.configuration.processor)
    developmentOnly(libs.spring.boot.devtools)

    implementation(libs.kotlinx.datetime)

    testImplementation(libs.bundles.spring.api.test.starter)
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("17"))
    }
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("build/**/*.kt", "bin/**/*.kt")
        ktlint()
    }
}

tasks {
    withType<KotlinJvmCompile> {
        kotlinOptions {
            jvmTarget = "17"
            apiVersion = "1.9"
            languageVersion = "1.9"
        }
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    test {
        useJUnitPlatform()

        systemProperties["karate.options"] = System.getProperties().getProperty("karate.options")
        systemProperties["karate.env"] = System.getProperties().getProperty("karate.env")

        outputs.upToDateWhen { false }

        if (project.hasProperty("excludeTests")) {
            filter {
                excludeTestsMatching(project.property("excludeTests") as String)
            }
        }
    }
}
