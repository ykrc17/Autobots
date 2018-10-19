package com.ykrc17.gradle.autobots.traverser

import com.android.build.api.transform.TransformOutputProvider

abstract class AbstractTraverser<I>(protected val outputProvider: TransformOutputProvider) {
    abstract fun traverse(input: I)
    abstract fun onFinish()
}