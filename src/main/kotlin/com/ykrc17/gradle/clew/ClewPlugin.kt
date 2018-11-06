package com.ykrc17.gradle.clew

import com.ykrc17.gradle.autobots.AutobotsPlugin
import com.ykrc17.gradle.autobots.Transformer
import com.ykrc17.gradle.autobots.TransformerConfig
import org.gradle.api.Project

@Suppress("unused")
class ClewPlugin : AutobotsPlugin() {
    override fun getTransformConfig(target: Project): TransformerConfig = object : TransformerConfig {
        override fun createTransformer(): Transformer {
            return ClewTransformer(target)
        }
    }
}