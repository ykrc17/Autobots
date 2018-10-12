package com.ykrc17.gradle.autobots.processor

import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInvocation
import com.android.utils.FileUtils

class JarProcessor(transformInvocation: TransformInvocation) : AbstractProcessor<JarInput>(transformInvocation) {
    var jarCount = 0

    override fun process(input: JarInput) {
        val jarOutput = outputProvider.getContentLocation(input.name, input.contentTypes, input.scopes, Format.JAR)
        FileUtils.copyFile(input.file, jarOutput)
//                println("${it.file} to ${jarOutput}")
        jarCount++
    }

    override fun onFinish() {
        println("\ttransformed jar: $jarCount")
    }
}
