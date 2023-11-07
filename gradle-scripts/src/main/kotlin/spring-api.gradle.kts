import gradle.kotlin.dsl.accessors._a568f78b412045bc377fc4283b656e34.annotationProcessor
import gradle.kotlin.dsl.accessors._a568f78b412045bc377fc4283b656e34.implementation
import gradle.kotlin.dsl.accessors._a568f78b412045bc377fc4283b656e34.processResources
import gradle.kotlin.dsl.accessors._a568f78b412045bc377fc4283b656e34.runtimeOnly
import gradle.kotlin.dsl.accessors._a568f78b412045bc377fc4283b656e34.test
import gradle.kotlin.dsl.accessors._a568f78b412045bc377fc4283b656e34.testImplementation
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.kotlin

plugins {
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.springdoc.openapi-gradle-plugin")
    id("org.dddjava.jig-gradle-plugin")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.44.1")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.44.1")
    runtimeOnly("org.postgresql:postgresql")

    implementation("ch.qos.logback:logback-access")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    "developmentOnly"("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.intuit.karate:karate-junit5:1.4.0")
    testImplementation("net.masterthought:cucumber-reporting:5.7.5")
    testImplementation("com.tngtech.archunit:archunit-junit5:1.0.1")
}

tasks {
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
