tasks {
    register("spotlessCheck") {
        group = "verification"
        subprojects.forEach {
            dependsOn(":${it.name}:spotlessCheck")
        }
    }
    register("spotlessApply") {
        group = "verification"
        subprojects.forEach {
            dependsOn(":${it.name}:spotlessApply")
        }
    }
    register("build") {
        group = "build"
        subprojects.forEach {
            dependsOn(":${it.name}:build")
        }
    }
}
