import io.ktor.plugin.features.DockerPortMapping
import io.ktor.plugin.features.DockerPortMappingProtocol
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktor.plugin)
    alias(libs.plugins.docker.compose)
    alias(libs.plugins.spotless)
}

repositories {
    mavenCentral()
    maven(url = "https://raw.githubusercontent.com/glureau/json-schema-serialization/mvn-repo")
}

dependencies {
    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.exposed.standalone)

    implementation(libs.bundles.koin.ktor)
    compileOnly(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    runtimeOnly(libs.postgresql.jdbc)

    implementation(libs.kotlinx.datetime)
    implementation(libs.yavi)

    testImplementation(libs.bundles.ktor.server.test)
    testImplementation(libs.bundles.ktor.client)

    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.mockk)
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

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("build/**/*.kt", "bin/**/*.kt")
        ktlint()
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
    useComposeFiles = listOf("../docker/compose.yml")
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
