pluginManagement {
    includeBuild("convention-plugins/project")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "XrayConfiguration"
include(":xray-configuration")
