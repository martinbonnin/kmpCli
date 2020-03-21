plugins {
    kotlin("multiplatform").version("1.3.70")
    id("application")
}

repositories {
    mavenCentral()
}

(extensions.findByName("application") as? JavaApplication)?.apply {
    mainClassName = "net.mbonnin.kmpcli.RootKt"
}

kotlin {
    jvm {
    }
    js {
        nodejs()
    }

    linuxX64()
    mingwX64()
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
                implementation("com.github.ajalt:clikt-multiplatform:2.6.0")
            }
        }
    }
}
