import buildlogic.catalog
import buildlogic.lib

plugins {
    id("KotlinConventions")
    application
}

dependencies {
    implementation(catalog.lib("clikt"))
}
