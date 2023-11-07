import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

plugins {
    kotlin("jvm") apply false
    kotlin("plugin.serialization")
}

repositories {
    maven(url = "https://raw.githubusercontent.com/glureau/json-schema-serialization/mvn-repo")
}

dependencies {
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")

    implementation("io.ktor:ktor-server-core:2.3.5")
    implementation("io.ktor:ktor-server-cio:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-server-status-pages:2.3.5")

    implementation("io.github.smiley4:ktor-swagger-ui:2.6.0")
    implementation("jp.kukv.ktor-extension-plugins:environment:0.1.2")
    implementation("com.github.Ricky12Awesome:json-schema-serialization:0.9.9")

    implementation("ch.qos.logback:logback-classic:1.4.11")

    implementation("org.jetbrains.exposed:exposed-core:0.44.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.44.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.44.1")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.44.1")

    runtimeOnly("org.postgresql:postgresql:42.5.4")
    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("am.ik.yavi:yavi:0.13.1")

    testImplementation("io.ktor:ktor-server-test-host:2.3.5")
    testImplementation("io.ktor:ktor-client-cio:2.3.5")
    testImplementation("io.ktor:ktor-client-content-negotiation:2.3.5")

    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    testImplementation("io.mockk:mockk:1.13.8")
}

tasks {
    test {
        useJUnitPlatform()

        jvmArgs("--add-opens", "java.base/java.util=ALL-UNNAMED")

        outputs.upToDateWhen { false }

        if (project.hasProperty("excludeTests")) {
            filter {
                excludeTestsMatching(project.property("excludeTests") as String)
            }
        }
    }
}
