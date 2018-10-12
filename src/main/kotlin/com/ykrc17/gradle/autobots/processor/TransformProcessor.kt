package com.ykrc17.gradle.autobots.processor

import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import org.gradle.api.Project

class TransformProcessor(transformInvocation: TransformInvocation, target: Project) : AbstractProcessor<TransformInput>(transformInvocation) {
    val startTime = System.currentTimeMillis()
    val dirProcessor = DirProcessor(this.transformInvocation, target)
    val jarProcessor = JarProcessor(this.transformInvocation)

    override fun process(input: TransformInput) {
        input.directoryInputs.forEach(dirProcessor::process)
        input.jarInputs.forEach(jarProcessor::process)
    }

    override fun onFinish() {
        println("> autobots transform finished:")
        println("\tspent time: ${System.currentTimeMillis() - startTime}")
        dirProcessor.onFinish()
        jarProcessor.onFinish()
    }

}
