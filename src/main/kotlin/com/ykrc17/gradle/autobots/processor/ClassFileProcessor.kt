package com.ykrc17.gradle.autobots.processor

import com.ykrc17.gradle.autobots.Transformer
import java.io.File

class ClassFileProcessor(private val inputDir: File, private val outputDir: File, private val transformer: Transformer) : AbstractProcessor<File, File>() {

    override fun shouldProcess(input: File): Boolean {
        val className = input.toRelativeString(inputDir).removeSuffix(".class").replace(File.separator, ".")
        return transformer.shouldProcessClass(className)
    }

    override fun process(input: File, output: File) {
        transformer.processFile(input, output, outputDir)
    }
}