package com.ykrc17.gradle.clew

import com.android.build.api.transform.TransformInput
import com.ykrc17.gradle.autobots.Transformer
import org.gradle.api.Project
import java.io.DataOutputStream
import java.io.File
import java.io.InputStream
import java.util.zip.ZipOutputStream

class ClewTransformer(target: Project) : Transformer {
    companion object {
        // TODO 从配置中读取
        val CLASS_WHITE_LIST = arrayOf("MainActivity")
    }

    private val editor = ClassEditor(target)

    override fun beforeTraverse(input: TransformInput) {
        input.directoryInputs.forEach { editor.appendClassPath(it.file.absolutePath) }
        input.jarInputs.forEach { editor.appendClassPath(it.file.absolutePath) }
    }

    override fun shouldProcessClass(className: String): Boolean {
        return if (className in CLASS_WHITE_LIST) {
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
        editor.read(inputStream) {
            toBytecode(DataOutputStream(output))
        }
    }

    override fun afterTraverse(input: TransformInput) {
    }
}