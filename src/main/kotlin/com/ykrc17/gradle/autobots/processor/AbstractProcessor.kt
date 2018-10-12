package com.ykrc17.gradle.autobots.processor

import com.android.build.api.transform.TransformInvocation

abstract class AbstractProcessor<I>(protected val transformInvocation: TransformInvocation) {
    protected val outputProvider = transformInvocation.outputProvider
    abstract fun process(input: I)
    abstract fun onFinish()
}