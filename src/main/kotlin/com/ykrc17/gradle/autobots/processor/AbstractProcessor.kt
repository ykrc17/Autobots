package com.ykrc17.gradle.autobots.processor

abstract class AbstractProcessor<I, O> {
    abstract fun shouldProcess(input: I): Boolean

    abstract fun process(input: I, output: O)
}
