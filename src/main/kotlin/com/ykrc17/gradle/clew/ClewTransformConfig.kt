package com.ykrc17.gradle.clew

import com.android.build.api.transform.TransformInput
import com.ykrc17.gradle.autobots.TransformConfig
import org.apache.commons.io.IOUtils
import org.gradle.api.Project
import java.io.File
import java.io.InputStream
import java.util.zip.ZipOutputStream

class ClewTransformConfig(target: Project) : TransformConfig {
    private val editor = ClassEditor(target)

    override fun beforeTraverse(input: TransformInput) {
        input.directoryInputs.forEach { editor.appendClassPath(it.file.absolutePath) }
        input.jarInputs.forEach { editor.appendClassPath(it.file.absolutePath) }
    }

    override fun shouldProcessClass(className: String): Boolean {
        return if (className == "MainActivity") {
            println("editing class: $className")
            true
        } else {
            false
        }
    }

    override fun processFile(inputFile: File, outputFile: File, outputDir: File) {
        editor.read(inputFile.inputStream()) {
            // TODO 直接使用output，而不是outputDir
            writeFile(outputDir.absolutePath)
        }
    }

    override fun processZipEntry(inputStream: InputStream, output: ZipOutputStream) {
        IOUtils.copy(inputStream, output)
    }

    override fun afterTraverse(input: TransformInput) {
    }
}