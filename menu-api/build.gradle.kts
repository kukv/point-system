import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"

    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"

    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"

    id("org.dddjava.jig-gradle-plugin") version "2023.10.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.44.1")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.44.1")
    runtimeOnly("org.postgresql:postgresql")

    implementation("ch.qos.logback:logback-access")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.intuit.karate:karate-junit5:1.4.1")
    testImplementation("net.masterthought:cucumber-reporting:5.7.7")
    testImplementation("com.tngtech.archunit:archunit-junit5:1.0.1")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("17"))
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
