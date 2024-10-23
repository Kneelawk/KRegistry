plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
    id("com.kneelawk.kpublish")
}

submodule {
    applyXplatConnection(":lite-xplat")
    setupJavadoc()
}

kpublish {
    createPublication()
}
