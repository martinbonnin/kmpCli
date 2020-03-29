plugins {
    kotlin("multiplatform").version("1.3.71")
    id("application")
}

repositories {
    jcenter()
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
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.5-native-mt")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.5-native-mt")
                implementation("io.ktor:ktor-client-core-native:1.3.2")
                implementation("io.ktor:ktor-client-logging-native:1.3.2")
            }
        }
        macosX64().compilations["main"].defaultSourceSet {
            dependencies {
                implementation("io.ktor:ktor-client-core-macosx64:1.3.2")
                implementation("io.ktor:ktor-client-curl:1.3.2")
            }
        }
    }
}
