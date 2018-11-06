package com.ykrc17.gradle.ext

import org.gradle.api.Plugin
import org.gradle.api.plugins.PluginContainer

inline fun <reified T : Plugin<*>> PluginContainer.findPlugin(): T? {
    return findPlugin(T::class.java)
}