
plugins {
    id("KotlinCliAppConventions")
}

application {
    mainClass = "uos.app.LauncherKt"
    applicationName = "uos"
}

dependencies {
    implementation(project(":commons"))
}