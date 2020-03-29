plugins {
    kotlin("multiplatform").version("1.3.71")
    id("application")
}

repositories {
    mavenCentral()
}

(extensions.findByName("application") as? JavaApplication)?.apply {
    mainClassName = "net.mbonnin.kmpcli.RootKt"
}

kotlin {
    macosX64 {
        binaries {
            executable("martinCli", listOf(DEBUG)) {
                entryPoint("net.mbonnin.kmpcli.main")
            }
        }
    }
    sourceSets {
        get("commonMain").apply {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.5")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.5")
            }
        }
    }
}
