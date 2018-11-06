package com.ykrc17.gradle.autobots.traverser

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.TransformInvocation
import com.android.utils.FileUtils
import com.ykrc17.gradle.autobots.Transformer
import com.ykrc17.gradle.autobots.processor.ClassFileProcessor
import java.io.File

class DirTraverser(transformInvocation: TransformInvocation, private val transformer: Transformer) : AbstractTraverser<DirectoryInput>(transformInvocation.outputProvider) {

    var dirCount = 0

    override fun traverse(input: DirectoryInput) {
        val inputDir = input.file
        val outputDir = outputProvider.getContentLocation(input.name, input.contentTypes, input.scopes, Format.DIRECTORY)
        val processor = ClassFileProcessor(inputDir, outputDir, transformer)
        traverseDirectory(processor, input.file, outputDir)
        dirCount++
    }

    private fun traverseDirectory(processor: ClassFileProcessor, from: File, to: File) {
        FileUtils.mkdirs(to)
        from.listFiles()?.forEach { child ->
            if (child.isFile) {
                traverseFile(processor, child, File(to, child.name))
            } else if (child.isDirectory) {
                traverseDirectory(processor, child, File(to, child.name))
            }
        }
    }

    private fun traverseFile(processor: ClassFileProcessor, from: File, to: File) {
        if (processor.shouldProcess(from)) {
            processor.process(from, to)
        } else {
            FileUtils.copyFile(from, to)
        }
    }

    override fun onFinish() {
        println("\ttransformed dir : $dirCount")
    }
}