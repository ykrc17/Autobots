package com.ykrc17.gradle.autobots

import org.gradle.api.Project

internal fun Project.logd(message: String) {
    logger.debug(message)
}

internal fun Project.loge(message: String) {
    logger.error("ERROR: $message")
}