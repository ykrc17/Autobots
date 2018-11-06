package com.ykrc17.gradle.autobots

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.builder.model.Version
import org.gradle.api.Plugin
import org.gradle.api.Project

const val MIN_AP_VERSION = "2.3"

@Suppress("unused")
class AutobotsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("> Autobots:")
        validateAndroidPlugin(target)
        target.extensions.getByType(AppExtension::class.java).registerTransform(AutobotsTransform(target))
    }

    private fun validateAndroidPlugin(target: Project) {
        target.plugins.findPlugin(AppPlugin::class.java)
                ?: error("Plugin \"com.android.application\" not found in project \"${target.path}\"")
        println("Plugin found: \"com.android.application:${Version.ANDROID_GRADLE_PLUGIN_VERSION}\"")

        if (!compareVersion(MIN_AP_VERSION, Version.ANDROID_GRADLE_PLUGIN_VERSION)) {
            error("Minimum supported Android Gradle Plugin version is $MIN_AP_VERSION")
        }
    }

    private fun compareVersion(minVersion: String, nowVersion: String): Boolean {
        val minSplits = minVersion.split(".")
        val nowSplits = nowVersion.split(".")
        for (i in 0 until minSplits.size) {
            if (i >= nowSplits.size) {
                return false
            }
            val m = minSplits[i].toInt()
            val c = nowSplits[i].toInt()
            when {
                c > m -> return true
                c < m -> return false
            }
        }
        return true
    }
}
