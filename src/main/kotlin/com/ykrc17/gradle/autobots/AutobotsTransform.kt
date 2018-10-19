package com.ykrc17.gradle.autobots

import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ykrc17.gradle.autobots.traverser.TransformTraverser
import org.gradle.api.Project

class AutobotsTransform(private val target: Project) : Transform() {

    override fun getName() = "autobots"

    override fun getInputTypes() = TransformManager.CONTENT_CLASS!!

    override fun getScopes() = TransformManager.SCOPE_FULL_PROJECT!!

    override fun isIncremental() = false

    override fun transform(transformInvocation: TransformInvocation) {
        val inputContainer = TransformInputContainer()
        transformInvocation.inputs.forEach(inputContainer::add)

        val transformProcessor = TransformTraverser(target, transformInvocation)
        transformProcessor.traverse(inputContainer)
        transformProcessor.onFinish()
    }
}