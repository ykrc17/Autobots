package com.ykrc17.gradle.autobots

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInput

class TransformInputContainer : TransformInput {

    private val directoryInputs = arrayListOf<DirectoryInput>()
    private val jarInputs = arrayListOf<JarInput>()

    override fun getDirectoryInputs(): MutableCollection<DirectoryInput> = directoryInputs

    override fun getJarInputs(): MutableCollection<JarInput> = jarInputs

    fun add(input: TransformInput) {
        directoryInputs.addAll(input.directoryInputs)
        jarInputs.addAll(input.jarInputs)
    }
}