package com.ykrc17.gradle.autobots

import com.android.build.api.transform.TransformInput
import java.io.File
import java.io.InputStream
import java.util.zip.ZipOutputStream

interface Transformer {
    fun beforeTraverse(input: TransformInput)

    fun shouldProcessClass(className: String): Boolean

    fun processFile(inputFile: File, outputFile: File, outputDir: File)

    fun processZipEntry(inputStream: InputStream, output: ZipOutputStream)

    fun afterTraverse(input: TransformInput)
}