package com.ykrc17.gradle.autobots.traverser

import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInvocation
import com.ykrc17.gradle.autobots.edit.ClassEditor
import com.ykrc17.gradle.autobots.processor.JarProcessor
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.util.concurrent.TimeUnit
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

class JarTraverser(transformInvocation: TransformInvocation, editor: ClassEditor) : AbstractTraverser<JarInput>(transformInvocation.outputProvider) {
    var totalTime = 0L
    lateinit var processor: JarProcessor

    override fun traverse(input: JarInput) {
        val timeSpan = System.nanoTime()

        val jarOutFile = outputProvider.getContentLocation(input.name, input.contentTypes, input.scopes, Format.JAR)
        traverseReadJar(input.file, jarOutFile)

        totalTime += Math.abs(System.nanoTime() - timeSpan)
    }

    private fun traverseReadJar(inFile: File, outFile: File) {
        val inJar = ZipFile(inFile)
        processor = JarProcessor(inJar)
        if (shouldAnyProcess(inJar)) {
            traverseCopyJar(inJar, outFile)
        } else {
            // 如果未命中白名单
            FileUtils.copyFile(inFile, outFile)
        }
    }

    private fun shouldAnyProcess(inJar: ZipFile): Boolean {
        inJar.entries().iterator().forEach {
            if (processor.shouldProcess(it)) {
                return true
            }
        }
        return false
    }

    private fun traverseCopyJar(inJar: ZipFile, outFile: File) {
        val outJar = ZipOutputStream(outFile.outputStream())

        inJar.entries().iterator().forEach {
            if (processor.shouldProcess(it)) {
                processor.process(it, outJar)
            } else {
                outJar.putNextEntry(it)
                IOUtils.copy(inJar.getInputStream(it), outJar)
                outJar.closeEntry()
            }
        }

        inJar.close()
        outJar.close()
    }

    override fun onFinish() {
        println("jar totalTime = ${TimeUnit.NANOSECONDS.toMillis(totalTime)}ms")
    }
}
