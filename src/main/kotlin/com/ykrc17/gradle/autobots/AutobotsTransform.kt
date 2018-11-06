package com.ykrc17.gradle.autobots

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ykrc17.gradle.autobots.traverser.TransformTraverser
import org.gradle.api.Project

class AutobotsTransform(private val target: Project) : Transform() {

    override fun getName() = "autobots"

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS

    override fun getScopes(): MutableSet<QualifiedContent.Scope> = TransformManager.SCOPE_FULL_PROJECT

    override fun isIncremental() = false

    override fun transform(transformInvocation: TransformInvocation) {
        val inputContainer = TransformInputContainer()
        transformInvocation.inputs.forEach(inputContainer::add)

        val transformTraverser = TransformTraverser(target, transformInvocation)
        transformTraverser.traverse(inputContainer)
        transformTraverser.onFinish()
    }
}