import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.serialization)
    id("publish.plugin")
}

group = "io.github.tim06.xray-configuration"
version = "1.0.8"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    jvm()

    /*js {
        browser {
            webpackTask {
                mainOutputFileName = "xray-configuration.js"
            }
        }
        binaries.executable()
    }*/

    /*wasmJs {
        browser()
        binaries.executable()
    }*/

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "xray-configuration"
            isStatic = true
            xcf.add(this)
        }
    }

    /*listOf(
        macosX64(),
        macosArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "xray-configuration"
            isStatic = true
        }
    }*/

    /*linuxX64 {
        binaries.staticLib {
            baseName = "xray-configuration"
        }
    }*/


    /*mingwX64 {
        binaries.staticLib {
            baseName = "xray-configuration"
        }
    }*/

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kermit)
            implementation(libs.urlencoder)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].compilerOptions.options.freeCompilerArgs.add("-Xexport-kdoc")
    }
}

android {
    namespace = "io.github.tim06"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildTypes {
        debug {  }
        release {  }
    }
}
