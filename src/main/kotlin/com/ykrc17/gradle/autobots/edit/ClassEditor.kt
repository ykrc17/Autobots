package com.ykrc17.gradle.autobots.edit

import com.android.build.gradle.AppExtension
import javassist.ClassPool
import javassist.CtClass
import javassist.Loader
import org.gradle.api.Project
import java.io.InputStream

class ClassEditor {
    private val pool: ClassPool

    constructor(project: Project) {
        pool = object : ClassPool(true) {
            override fun getClassLoader(): ClassLoader {
                return Loader()
            }
        }
//        pool.appendSystemPath()
        pool.appendClassPath(project.extensions.getByType(AppExtension::class.java).bootClasspath[0].absolutePath)
    }

    fun appendClassPath(pathname: String) {
        pool.appendClassPath(pathname)
    }

    fun read(inputStream: InputStream, onRequireWrite: CtClass.() -> Unit) {
        val ctClass = pool.makeClass(inputStream)
        ctClass.declaredMethods.forEach {
            it.addLocalVariable("ttttt", CtClass.longType)
            it.insertBefore("ttttt = System.currentTimeMillis();")
            it.insertAfter("android.util.Log.d(\"Autobots\",\"${it.name} \" + (System.currentTimeMillis() - ttttt) + \"ms\");")
        }
        onRequireWrite(ctClass)
        ctClass.detach()
    }
}
