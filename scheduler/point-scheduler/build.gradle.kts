tasks {
    register("spotlessCheck") {
        group = "verification"
        subprojects.forEach {
            dependsOn(":${it.name}:spotlessCheck")
        }
    }
    register("build") {
        group = "build"
        subprojects.forEach {
            dependsOn(":${it.name}:build")
        }
    }
}
