@file:Suppress("UnstableApiUsage")

import buildlogic.catalog
import buildlogic.lib
import org.gradle.kotlin.dsl.support.useToRun
import java.util.Properties
import java.io.File

plugins {
    id("JavaConventions")
    id("org.jetbrains.kotlin.jvm")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            dependencies {
                implementation(catalog.lib("kotest-runner-junit5"))
                implementation(catalog.lib("kotest-assertions-core"))
                implementation(catalog.lib("kotest-framework-datatest"))
                implementation(catalog.lib("kotlin-reflect"))
            }
        }
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    compilerOptions {
        freeCompilerArgs.add("-Xwhen-guards")
        freeCompilerArgs.add("-Xnon-local-break-continue")
        freeCompilerArgs.add("-Xmulti-dollar-interpolation")
    }
}

tasks {
    val optimizeKotestRunner by registering {
        group = "verification"
        description = "Optimize kotest runner."
        val optimizationsFile by lazy { project.layout.projectDirectory.file("src/test/resources/kotest.properties").asFile }
        doLast {
            val optimizations = listOf(
                "kotest.framework.classpath.scanning.config.disable" to "true",
                "kotest.framework.classpath.scanning.autoscan.disable" to "true",
            )
            val properties = Properties().apply {
                if (optimizationsFile.exists())
                    optimizationsFile.inputStream().use(::load)
            }
            val enforceOptimizations = optimizations.fold(false) { acc, (property, value) ->
                val updatable = (property !in properties)
                if (updatable) properties.put(property, value)
                acc || updatable
            }
            if (enforceOptimizations) {
                if (!optimizationsFile.parentFile.takeUnless(File::exists)?.mkdirs().let { it==null || !it })
                    throw GradleException("Unable to create correct resources directory: ${optimizationsFile.parent}")
                optimizationsFile.writer().useToRun { properties.store(this, "Applied optimizations for kotest by task $name") }
            }
        }
    }
}
