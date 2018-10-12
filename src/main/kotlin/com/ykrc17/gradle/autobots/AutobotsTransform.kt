package com.ykrc17.gradle.autobots

import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ykrc17.gradle.autobots.processor.TransformProcessor
import org.gradle.api.Project

class AutobotsTransform(private val target: Project) : Transform() {

    override fun getName() = "autobots"

    override fun getInputTypes() = TransformManager.CONTENT_CLASS!!

    override fun getScopes() = TransformManager.SCOPE_FULL_PROJECT!!

    override fun isIncremental() = false

    override fun transform(transformInvocation: TransformInvocation) {
        val transformProcessor = TransformProcessor(transformInvocation, target)
        transformInvocation.inputs.forEach(transformProcessor::process)
        transformProcessor.onFinish()
    }
}