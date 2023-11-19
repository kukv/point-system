rootProject.name = "izakaya-wagokoro"

file("api").listFiles()?.forEach { module: File ->
    includeBuild(module)
}

file("scheduler").listFiles()?.forEach { module: File ->
    includeBuild(module)
}
