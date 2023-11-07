import io.ktor.plugin.features.DockerPortMapping
import io.ktor.plugin.features.DockerPortMappingProtocol

plugins {
    application

    id("kotlin-base")
    id("spotless")
    id("ktor-base")
    id("ktor-koin")

    id("io.ktor.plugin") version "2.3.5"
    id("com.avast.gradle.docker-compose") version "0.17.5"
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("jp.kukv.point.ApplicationKt")
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
