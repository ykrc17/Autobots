package com.ykrc17.gradle.autobots

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class AutobotsPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        if (plugins.hasPlugin(AppPlugin::class.java)) {
            logd("Plugin found: \"com.android.application\"")
            extensions.getByType(AppExtension::class.java).registerTransform(AutobotsTransform())
        } else {
            loge("Cannot find plugin: \"com.android.application\"")
        }
        Unit
    }
}
