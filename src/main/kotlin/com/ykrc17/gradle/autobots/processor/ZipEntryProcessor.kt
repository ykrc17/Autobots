package com.ykrc17.gradle.autobots.processor

import com.ykrc17.gradle.autobots.Transformer
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

class ZipEntryProcessor(private val inZip: ZipFile, private val transformer: Transformer) : AbstractProcessor<ZipEntry, ZipOutputStream>() {

    override fun shouldProcess(input: ZipEntry): Boolean {
        val className = input.name.removeSuffix(".class").replace(File.separator, ".")
        return transformer.shouldProcessClass(className)
    }

    override fun process(input: ZipEntry, output: ZipOutputStream) {
        output.putNextEntry(ZipEntry(input.name))
        transformer.processZipEntry(inZip.getInputStream(input), output)
        output.closeEntry()
    }
}