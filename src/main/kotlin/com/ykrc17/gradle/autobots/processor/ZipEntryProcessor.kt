package com.ykrc17.gradle.autobots.processor

import org.apache.commons.io.IOUtils
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

class ZipEntryProcessor(private val inZip: ZipFile) : AbstractProcessor<ZipEntry, ZipOutputStream>() {

    override fun shouldProcess(input: ZipEntry) = shouldProcess()

    private fun shouldProcess(): Boolean {
        return true
    }

    override fun process(input: ZipEntry, output: ZipOutputStream) {
        output.putNextEntry(input)
        IOUtils.copy(inZip.getInputStream(input), output)
        output.closeEntry()
    }
}