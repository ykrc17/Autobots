package com.ykrc17.gradle.autobots.traverser

import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.ykrc17.gradle.autobots.TransformConfig

class TransformTraverser(private val transformInvocation: TransformInvocation, private val config: TransformConfig) : AbstractTraverser<TransformInput>(transformInvocation.outputProvider) {
    private val startTime = System.currentTimeMillis()

    override fun traverse(input: TransformInput) {
        config.beforeTraverse(input)

        // 处理
        input.directoryInputs.forEach(DirTraverser(transformInvocation, config)::traverse)
        input.jarInputs.forEach(JarTraverser(transformInvocation, config)::traverse)

        config.afterTraverse(input)
    }

    override fun onFinish() {
        println("> autobots transform finished:")
        println("\tspent time: %,dms".format(System.currentTimeMillis() - startTime))
    }

}
