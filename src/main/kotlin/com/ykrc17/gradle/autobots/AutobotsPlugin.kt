package com.ykrc17.gradle.autobots

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.builder.model.Version
import com.ykrc17.gradle.ext.findPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class AutobotsPlugin : Plugin<Project> {
    companion object CONSTANTS {
        const val MIN_AP_VERSION = "2.3"
    }

    override fun apply(target: Project) {
        println("> Autobots:")
        validateAndroidPlugin(target)
        target.extensions.getByType(AppExtension::class.java).registerTransform(AutobotsTransform(getTransformConfig(target)))
    }

    open fun getMinAndroidPluginVersion(): String = MIN_AP_VERSION

    abstract fun getTransformConfig(target: Project): TransformerConfig

    private fun validateAndroidPlugin(target: Project) {
        target.plugins.findPlugin<AppPlugin>()
                ?: error("Plugin \"com.android.application\" not found in project \"${target.path}\"")
        println("Plugin found: \"com.android.application:${Version.ANDROID_GRADLE_PLUGIN_VERSION}\"")

        if (!compareVersion(getMinAndroidPluginVersion(), Version.ANDROID_GRADLE_PLUGIN_VERSION)) {
            error("Minimum supported Android Gradle Plugin version is ${getMinAndroidPluginVersion()}")
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
