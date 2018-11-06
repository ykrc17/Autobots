package com.ykrc17.gradle.autobots.traverser

import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInvocation
import com.ykrc17.gradle.autobots.TransformConfig
import com.ykrc17.gradle.autobots.processor.ZipEntryProcessor
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.util.concurrent.TimeUnit
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

class JarTraverser(transformInvocation: TransformInvocation, val config: TransformConfig) : AbstractTraverser<JarInput>(transformInvocation.outputProvider) {
    var totalTime = 0L

    override fun traverse(input: JarInput) {
        val timeSpan = System.nanoTime()

        val jarOutFile = outputProvider.getContentLocation(input.name, input.contentTypes, input.scopes, Format.JAR)
        traverseCopyJar(input, jarOutFile)

        totalTime += Math.abs(System.nanoTime() - timeSpan)
    }

    private fun traverseCopyJar(jarInput: JarInput, outFile: File) {
        val inZip = ZipFile(jarInput.file)
        val processor = ZipEntryProcessor(inZip, config)
        if (shouldProcess(processor, jarInput, inZip)) {
            println("> copying zip: ${jarInput.file.path}")
            copyJarEntries(processor, inZip, outFile)
        } else {
            // 如果未命中白名单
            FileUtils.copyFile(jarInput.file, outFile)
        }
    }

    private fun shouldProcess(processor: ZipEntryProcessor, jarInput: JarInput, inZip: ZipFile): Boolean {
        // 只修改项目中的jar
        if (jarInput.scopes.contains(QualifiedContent.Scope.EXTERNAL_LIBRARIES)) {
            return false
        }
        // jar中文件命中白名单
        inZip.entries().iterator().forEach {
            if (processor.shouldProcess(it)) {
                return true
            }
        }
        return false
    }

    private fun copyJarEntries(processor: ZipEntryProcessor, inJar: ZipFile, outFile: File) {
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
