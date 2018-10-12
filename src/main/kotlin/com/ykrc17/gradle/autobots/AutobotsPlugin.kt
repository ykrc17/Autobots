package com.ykrc17.gradle.autobots

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.internal.Version
import org.gradle.api.Plugin
import org.gradle.api.Project

const val MIN_AP_VERSION = "2.3"

@Suppress("unused")
class AutobotsPlugin : Plugin<Project> {
    override fun apply(target: Project) = withProject(target) {
        println("> Autobots:")
        val plugin = plugins.findPlugin(AppPlugin::class.java)
        if (plugin != null) {
            log("Plugin found: \"com.android.application:${Version.ANDROID_GRADLE_PLUGIN_VERSION}\"")
            if (checkAPVersion()) {
                extensions.getByType(AppExtension::class.java).registerTransform(AutobotsTransform())
            } else {
                log("Minimum supported Android Gradle Plugin version is $MIN_AP_VERSION")
            }
        } else {
            loge("Cannot find plugin: \"com.android.application\"")
        }
        println()
    }

    private fun checkAPVersion(): Boolean {
        val minVersion = MIN_AP_VERSION.split(".")
        val currentVersion = Version.ANDROID_GRADLE_PLUGIN_VERSION.split(".")
        for (i in 0 until minVersion.size) {
            if (i >= currentVersion.size) {
                return false
            }
            val m = minVersion[i].toInt()
            val c = currentVersion[i].toInt()
            when {
                c > m -> return true
                c < m -> return false
            }
        }
        return true
    }

    private inline fun withProject(project: Project, block: Project.() -> Unit) {
        block(project)
    }
}
