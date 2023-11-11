import io.ktor.plugin.features.DockerPortMapping
import io.ktor.plugin.features.DockerPortMappingProtocol
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    application

    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"

    id("com.google.devtools.ksp") version "1.9.20-1.0.14"

    id("io.ktor.plugin") version "2.3.5"

    id("org.dddjava.jig-gradle-plugin") version "2023.10.1"

    id("com.avast.gradle.docker-compose") version "0.17.5"
}

repositories {
    mavenCentral()
    maven(url = "https://raw.githubusercontent.com/glureau/json-schema-serialization/mvn-repo")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")

    implementation("io.ktor:ktor-server-core:2.3.5")
    implementation("io.ktor:ktor-server-cio:2.3.5")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-server-status-pages:2.3.5")

    implementation("io.github.smiley4:ktor-swagger-ui:2.6.0")
    implementation("jp.kukv.ktor-extension-plugins:environment:0.1.2")
    implementation("com.github.Ricky12Awesome:json-schema-serialization:0.9.9")

    implementation("ch.qos.logback:logback-classic:1.4.11")

    implementation("io.insert-koin:koin-ktor:3.5.1")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.1")

    compileOnly("io.insert-koin:koin-annotations:1.3.0")
    ksp("io.insert-koin:koin-ksp-compiler:1.3.0")

    implementation("org.jetbrains.exposed:exposed-core:0.44.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.44.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.44.1")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.44.1")

    runtimeOnly("org.postgresql:postgresql:42.5.4")
    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("am.ik.yavi:yavi:0.13.1")

    testImplementation("io.ktor:ktor-server-test-host:2.3.5")
    testImplementation("io.ktor:ktor-client-cio:2.3.6")
    testImplementation("io.ktor:ktor-client-content-negotiation:2.3.6")

    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    testImplementation("io.mockk:mockk:1.13.8")

    testImplementation("io.insert-koin:koin-test:3.5.0")
    testImplementation("io.insert-koin:koin-test-junit5:3.5.0")
}

application {
    mainClass.set("jp.kukv.point.ApplicationKt")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("17"))
    }
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
    sourceSets.test {
        kotlin.srcDir("build/generated/ksp/test/kotlin")
    }
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_17)
        localImageName.set("point-api")

        val portMap = listOf(DockerPortMapping(8081, 8080, DockerPortMappingProtocol.TCP))
        portMappings.set(portMap)
    }
}

dockerCompose {
    isRequiredBy(project.tasks.test)
    useComposeFiles = listOf("../docker/compose-unit-test.yml")
}

tasks {
    withType<KotlinJvmCompile> {
        kotlinOptions {
            jvmTarget = "17"
            apiVersion = "1.9"
            languageVersion = "1.9"
        }
    }

    test {
        useJUnitPlatform()
        jvmArgs("--add-opens", "java.base/java.util=ALL-UNNAMED")
        outputs.upToDateWhen { false }
    }
}
