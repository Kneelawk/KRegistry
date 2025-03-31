plugins {
    id("com.kneelawk.versioning")
    id("com.kneelawk.submodule")
}

submodule {
    setRefmaps("kregistry_simple_example_core")
    xplatProjectDependency(":core")
}
