package com.ykrc17.gradle.autobots.traverser

import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.ykrc17.gradle.autobots.Transformer

class TransformTraverser(private val transformInvocation: TransformInvocation, private val transformer: Transformer) : AbstractTraverser<TransformInput>(transformInvocation.outputProvider) {
    private val startTime = System.currentTimeMillis()

    override fun traverse(input: TransformInput) {
        transformer.beforeTraverse(input)

        // 处理
        input.directoryInputs.forEach(DirTraverser(transformInvocation, transformer)::traverse)
        input.jarInputs.forEach(JarTraverser(transformInvocation, transformer)::traverse)

        transformer.afterTraverse(input)
    }

    override fun onFinish() {
        println("> autobots transform finished:")
        println("\tspent time: %,dms".format(System.currentTimeMillis() - startTime))
    }

}
