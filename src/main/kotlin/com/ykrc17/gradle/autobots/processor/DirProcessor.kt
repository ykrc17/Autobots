package com.ykrc17.gradle.autobots.processor

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.TransformInvocation
import com.android.utils.FileUtils
import com.ykrc17.gradle.autobots.edit.ClassEditor
import org.gradle.api.Project
import java.io.File

class DirProcessor : AbstractProcessor<DirectoryInput> {

    val editor: ClassEditor
    var dirCount = 0
    lateinit var inputDir: File
    lateinit var outputDir: File

    constructor(transformInvocation: TransformInvocation, project: Project) : super(transformInvocation) {
        editor = ClassEditor(project)
    }

    override fun process(input: DirectoryInput) {
        inputDir = input.file
        editor.appendClassPath(inputDir.absolutePath)
        outputDir = outputProvider.getContentLocation(input.name, input.contentTypes, input.scopes, Format.DIRECTORY)
        // 清理缓存
        if (!transformInvocation.isIncremental) {
            FileUtils.cleanOutputDir(outputDir)
        }
        traverseDirectory(input.file, outputDir)
//        FileUtils.copyDirectory(input.file, directoryOutput)
        dirCount++
    }

    private fun traverseDirectory(from: File, to: File) {
        FileUtils.mkdirs(to)
        from.listFiles()?.forEach { child ->
            if (child.isFile) {
                traverseFile(child, File(to, child.name))
            } else if (child.isDirectory) {
                traverseDirectory(child, File(to, child.name))
            }
        }
    }

    private fun traverseFile(from: File, to: File) {
        val className = from.toRelativeString(inputDir).removeSuffix(".class").replace(File.separator, ".")
        if (className.contains("MainActivity")) {
            println("editing class: $className")
            editor.read(from.inputStream()) {
                writeFile(outputDir.absolutePath)
            }
        } else {
            FileUtils.copyFile(from, to)
        }
    }

    override fun onFinish() {
        println("\ttransformed dir : $dirCount")
    }
}