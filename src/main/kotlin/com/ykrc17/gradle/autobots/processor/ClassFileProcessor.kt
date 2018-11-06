package com.ykrc17.gradle.autobots.processor

import com.ykrc17.gradle.autobots.TransformConfig
import java.io.File

class ClassFileProcessor(private val inputDir: File, private val outputDir: File, private val config: TransformConfig) : AbstractProcessor<File, File>() {

    override fun shouldProcess(input: File): Boolean {
        val className = input.toRelativeString(inputDir).removeSuffix(".class").replace(File.separator, ".")
        return config.shouldProcessClass(className)
    }

    override fun process(input: File, output: File) {
        config.processFile(input, output, outputDir)
    }
}