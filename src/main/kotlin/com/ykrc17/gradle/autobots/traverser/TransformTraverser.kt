package com.ykrc17.gradle.autobots.traverser

import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.ykrc17.gradle.autobots.edit.ClassEditor
import org.gradle.api.Project

class TransformTraverser(target: Project, val transformInvocation: TransformInvocation) : AbstractTraverser<TransformInput>(transformInvocation.outputProvider) {
    private val editor = ClassEditor(target)
    private val startTime = System.currentTimeMillis()

    override fun traverse(input: TransformInput) {
        // 收集
        input.directoryInputs.forEach { editor.appendClassPath(it.file.absolutePath) }
        input.jarInputs.forEach { editor.appendClassPath(it.file.absolutePath) }

        // 处理
        input.directoryInputs.forEach(DirTraverser(transformInvocation, editor)::traverse)
        input.jarInputs.forEach(JarTraverser(transformInvocation, editor)::traverse)
    }

    override fun onFinish() {
        println("> autobots transform finished:")
        println("\tspent time: ${System.currentTimeMillis() - startTime}")
    }

}
