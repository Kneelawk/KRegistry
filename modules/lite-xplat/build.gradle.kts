plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
    id("com.kneelawk.kpublish")
}

submodule {
    setRefmaps("kregistry_lite")
    setupJavadoc()
}

kpublish {
    createPublication("intermediary")
}
