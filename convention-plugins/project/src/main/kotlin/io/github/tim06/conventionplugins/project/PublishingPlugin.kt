package io.github.tim06.conventionplugins.project

import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class PublishingPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        pluginManager.apply("maven-publish")
        pluginManager.apply("com.vanniktech.maven.publish")
        pluginManager.apply("signing")

        extensions.configure<com.vanniktech.maven.publish.MavenPublishBaseExtension> {
            publishToMavenCentral(SonatypeHost.S01)
            signAllPublications()

            pom {
                name.set("Xray-configuration")
                description.set("KMP Xray configuration library.")
                inceptionYear.set("2024")
                url.set("https://github.com/tim06/XrayConfiguration/")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("tim06")
                        name.set("Timur Hojatov")
                        url.set("https://github.com/tim06")
                        email.set("timhod06@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/tim06/XrayConfiguration/")
                    connection.set("scm:git:git://github.com/tim06/XrayConfiguration.git")
                    developerConnection.set("scm:git:ssh://git@github.com:tim06/XrayConfiguration.git")
                }
            }
        }
    }
}