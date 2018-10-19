package com.ykrc17.gradle.autobots.processor

import org.apache.commons.io.IOUtils
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

class JarProcessor(val inJar: ZipFile) : AbstractProcessor<ZipEntry, ZipOutputStream>() {
    override fun shouldProcess(input: ZipEntry): Boolean {
        return true
    }

    override fun process(input: ZipEntry, output: ZipOutputStream) {
        output.putNextEntry(input)
        IOUtils.copy(inJar.getInputStream(input), output)
        output.closeEntry()
    }
}