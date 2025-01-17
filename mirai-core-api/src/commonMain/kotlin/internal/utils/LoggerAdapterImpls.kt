/*
 * Copyright 2020 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/master/LICENSE
 */

package net.mamoe.mirai.internal.utils

import net.mamoe.mirai.utils.MiraiLoggerPlatformBase
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.MarkerManager
import org.apache.logging.log4j.message.Message
import org.apache.logging.log4j.message.SimpleMessage
import org.apache.logging.log4j.message.SimpleMessageFactory
import java.util.logging.Level as JulLevel
import java.util.logging.Logger as JulLogger

internal class Log4jLoggerAdapter(
    private val logger: org.apache.logging.log4j.Logger,
    override val marker: Marker?,
) : MiraiLoggerPlatformBase(), MarkedMiraiLogger {
    val factory: SimpleMessageFactory = SimpleMessageFactory.INSTANCE

    override fun verbose0(message: String?, e: Throwable?) {
        val marker = marker
        if (marker != null) logger.trace(marker, factory.newMessage(message), e)
        else logger.trace(factory.newMessage(message), e)
    }

    override fun debug0(message: String?, e: Throwable?) {
        val marker = marker
        if (marker != null) logger.debug(marker, factory.newMessage(message), e)
        else logger.debug(factory.newMessage(message), e)
    }

    override fun info0(message: String?, e: Throwable?) {
        val marker = marker
        if (marker != null) logger.info(marker, factory.newMessage(message), e)
        else logger.info(factory.newMessage(message), e)
    }

    override fun warning0(message: String?, e: Throwable?) {
        val marker = marker
        if (marker != null) logger.warn(marker, factory.newMessage(message), e)
        else logger.warn(factory.newMessage(message), e)
    }

    override fun error0(message: String?, e: Throwable?) {
        val marker = marker
        if (marker != null) logger.error(marker, factory.newMessage(message), e)
        else logger.error(factory.newMessage(message), e)
    }

    override val isVerboseEnabled: Boolean get() = logger.isTraceEnabled
    override val isDebugEnabled: Boolean get() = logger.isDebugEnabled
    override val isInfoEnabled: Boolean get() = logger.isInfoEnabled
    override val isWarningEnabled: Boolean get() = logger.isWarnEnabled
    override val isErrorEnabled: Boolean get() = logger.isErrorEnabled

    override val identity: String? get() = logger.name

    override fun subLogger(name: String): MarkedMiraiLogger {
        return Log4jLoggerAdapter(logger, Marker(name, marker))
    }

}

internal val MARKER_MIRAI by lazy { MarkerManager.getMarker("mirai") }

internal class Slf4jLoggerAdapter(private val logger: org.slf4j.Logger, private val marker: org.slf4j.Marker?) :
    MiraiLoggerPlatformBase() {
    override fun verbose0(message: String?, e: Throwable?) {
        if (marker == null) logger.trace(message, e)
        else logger.trace(marker, message, e)
    }

    override fun debug0(message: String?, e: Throwable?) {
        if (marker == null) logger.debug(message, e)
        else logger.debug(marker, message, e)
    }

    override fun info0(message: String?, e: Throwable?) {
        if (marker == null) logger.info(message, e)
        else logger.info(marker, message, e)
    }

    override fun warning0(message: String?, e: Throwable?) {
        if (marker == null) logger.warn(message, e)
        else logger.warn(marker, message, e)
    }

    override fun error0(message: String?, e: Throwable?) {
        if (marker == null) logger.error(message, e)
        else logger.error(marker, message, e)
    }

    override val isVerboseEnabled: Boolean get() = logger.isTraceEnabled
    override val isDebugEnabled: Boolean get() = logger.isDebugEnabled
    override val isInfoEnabled: Boolean get() = logger.isInfoEnabled
    override val isWarningEnabled: Boolean get() = logger.isWarnEnabled
    override val isErrorEnabled: Boolean get() = logger.isErrorEnabled

    override val identity: String?
        get() = logger.name
}

internal class JdkLoggerAdapter(private val logger: JulLogger) : MiraiLoggerPlatformBase() {
    override fun verbose0(message: String?, e: Throwable?) {
        logger.log(JulLevel.FINEST, message, e)
    }

    override fun debug0(message: String?, e: Throwable?) {
        logger.log(JulLevel.FINER, message, e)

    }

    override fun info0(message: String?, e: Throwable?) {
        logger.log(JulLevel.INFO, message, e)
    }

    override fun warning0(message: String?, e: Throwable?) {
        logger.log(JulLevel.WARNING, message, e)
    }

    override fun error0(message: String?, e: Throwable?) {
        logger.log(JulLevel.SEVERE, message, e)
    }

    override val isVerboseEnabled: Boolean get() = logger.isLoggable(JulLevel.FINE)
    override val isDebugEnabled: Boolean get() = logger.isLoggable(JulLevel.FINEST)
    override val isInfoEnabled: Boolean get() = logger.isLoggable(JulLevel.INFO)
    override val isWarningEnabled: Boolean get() = logger.isLoggable(JulLevel.WARNING)
    override val isErrorEnabled: Boolean get() = logger.isLoggable(JulLevel.SEVERE)

    override val identity: String?
        get() = logger.name
}
