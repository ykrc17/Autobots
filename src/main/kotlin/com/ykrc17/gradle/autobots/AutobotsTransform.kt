package com.ykrc17.gradle.autobots

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ykrc17.gradle.autobots.traverser.TransformTraverser

class AutobotsTransform(val config: TransformConfig) : Transform() {

    override fun getName() = "autobots"

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<QualifiedContent.Scope> = TransformManager.SCOPE_FULL_PROJECT

    override fun isIncremental() = true

    override fun transform(transformInvocation: TransformInvocation) {
        if (!transformInvocation.isIncremental) {
            transformInvocation.outputProvider.deleteAll()
        }

        // 收集input
        val inputContainer = TransformInputContainer()
        transformInvocation.inputs.forEach(inputContainer::add)

        // 遍历input
        val transformTraverser = TransformTraverser(transformInvocation, config)
        transformTraverser.traverse(inputContainer)
        transformTraverser.onFinish()
    }
}