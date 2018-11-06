package com.ykrc17.gradle.autobots.processor

import com.ykrc17.gradle.autobots.edit.ClassEditor
import java.io.File

class ClassFileProcessor(val inputDir: File, val outputDir: File, val editor: ClassEditor) : AbstractProcessor<File, File>() {

    override fun shouldProcess(input: File): Boolean {
        val className = input.toRelativeString(inputDir).removeSuffix(".class").replace(File.separator, ".")
        return if (className.endsWith("MainActivity")) {
            println("editing class: $className")
            true
        } else {
            false
        }
    }

    override fun process(input: File, output: File) {
        editor.read(input.inputStream()) {
            // TODO 直接使用output，而不是outputDir
            writeFile(outputDir.absolutePath)
        }
    }
}