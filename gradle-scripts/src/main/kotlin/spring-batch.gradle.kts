import gradle.kotlin.dsl.accessors._35643e33f7d1312effc9c7ed341a53e0.implementation
import gradle.kotlin.dsl.accessors._35643e33f7d1312effc9c7ed341a53e0.processResources
import gradle.kotlin.dsl.accessors._35643e33f7d1312effc9c7ed341a53e0.runtimeOnly
import gradle.kotlin.dsl.accessors._35643e33f7d1312effc9c7ed341a53e0.test
import gradle.kotlin.dsl.accessors._35643e33f7d1312effc9c7ed341a53e0.testImplementation

plugins {
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.dddjava.jig-gradle-plugin")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.44.1")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.44.1")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    test {
        useJUnitPlatform()
    }
}
