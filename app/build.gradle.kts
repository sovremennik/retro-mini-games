import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.io.File

plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.compose") version "2.1.10"
    id("org.jetbrains.compose") version "1.7.3"
}

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation("org.jetbrains.compose.desktop:desktop-jvm-windows-x64:1.7.3")

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.8")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Exe)
            packageName = "Snake"
            packageVersion = "1.0.0"
            windows {
                iconFile.set(File("src/main/resources/snake_logo.ico"))
            }
        }
    }
}
