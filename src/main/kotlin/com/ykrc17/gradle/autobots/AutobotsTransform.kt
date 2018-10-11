package com.ykrc17.gradle.autobots

import com.android.build.api.transform.Format
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

class AutobotsTransform : Transform() {

    override fun getName() = "autobots"

    override fun getInputTypes() = TransformManager.CONTENT_CLASS!!

    override fun getScopes() = TransformManager.SCOPE_FULL_PROJECT!!

    override fun isIncremental() = false

    override fun transform(transformInvocation: TransformInvocation) {
        var dirCount = 0
        var jarCount = 0
        val startTime = System.currentTimeMillis()
        transformInvocation.inputs.forEach { input ->
            input.directoryInputs.forEach {
                val directoryOutput = transformInvocation.outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(it.file, directoryOutput)
//                println("${it.file} to ${directoryOutput}")
                dirCount++
            }
            input.jarInputs.forEach {
                val jarOutput = transformInvocation.outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.JAR)
                FileUtils.copyFile(it.file, jarOutput)
//                println("${it.file} to ${jarOutput}")
                jarCount++
            }
        }
        println("""> autobots transform finished:
    spent time: ${System.currentTimeMillis() - startTime}
    transformed dir: $dirCount
    transformed jar: $jarCount"""")
    }
}