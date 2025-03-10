rootProject.name = "uos"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention").version("0.9.0")
}

includeBuild("build-logic")

include(":app")
include(":commons")

