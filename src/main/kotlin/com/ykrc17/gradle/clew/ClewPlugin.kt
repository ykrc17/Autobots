package com.ykrc17.gradle.clew

import com.ykrc17.gradle.autobots.AutobotsPlugin
import org.gradle.api.Project

@Suppress("unused")
class ClewPlugin : AutobotsPlugin() {
    override fun getTransformConfig(target: Project) = ClewTransformConfig(target)
}