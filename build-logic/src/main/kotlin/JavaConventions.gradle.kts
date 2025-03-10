@file:Suppress("UnstableApiUsage")

import buildlogic.catalog
import buildlogic.lib
import buildlogic.version

plugins {
    `java-library`
}

repositories.mavenCentral()

if (rootProject.version.toString().contains("SNAPSHOT", ignoreCase = true))
    repositories.maven("https://oss.sonatype.org/content/repositories/snapshots")

the<JavaPluginExtension>().toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
    vendor.set(JvmVendorSpec.AZUL)
}

the<TestingExtension>().suites.apply {
    val test: JvmTestSuite by getting(JvmTestSuite::class) {
        useJUnitJupiter(catalog.version("junit-jupiter").preferredVersion)
        dependencies {
            implementation(catalog.lib("junit-jupiter-api"))
            implementation(catalog.lib("junit-jupiter-engine"))
        }
    }
}

tasks.withType<JavaCompile>().configureEach { options.encoding = "UTF-8" }
