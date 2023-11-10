tasks {
    register("build") {
        group = "build"
        subprojects.forEach {
            dependsOn("${it.name}:build")
        }
    }
    register("clean") {
        group = "build"
        subprojects.forEach {
            dependsOn("${it.name}:clean")
        }
    }
}
