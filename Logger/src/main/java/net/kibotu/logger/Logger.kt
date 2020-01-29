package net.kibotu.logger

import android.app.Application
import net.kibotu.ContextHelper
import java.util.concurrent.CopyOnWriteArrayList


/**
 * Static logging facet for concrete device specific logger.
 * Depending on Logging level it shows only higher prioritized logs.
 * For instance: if the Logging Level is set to info, no debug messages will be shown, etc.
 *
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

object Logger {

    /**
     * Logging level.
     */
    @JvmStatic
    var level: Level = Level.VERBOSE
        set(value) {
            val list = loggers.map { it.copy(second = value) }
            loggers.clear()
            loggers.addAll(list)
            field = value
        }

    /**
     * Concrete Logger.
     */
    private var loggers = CopyOnWriteArrayList<Pair<ILogger, Level>>()

    @Deprecated("no longer required", ReplaceWith(""))
    @JvmStatic
    fun with(context: Application) = Unit

    @Deprecated("no longer required", ReplaceWith(""))
    @JvmStatic
    fun onTerminate() {
        ContextHelper.onTerminate()
    }

    /**
     * Constructor.
     *
     * @param logger - Concrete Logger.
     */
    @JvmStatic
    fun addLogger(logger: ILogger, level: Level = Level.VERBOSE) = loggers.add(Pair(logger, level))

    /**
     * Constructor.
     *
     * @param logger - Concrete Logger.
     */
    @JvmStatic
    fun removeLogger(logger: ILogger) = loggers.removeAll { it.first == logger }

    @JvmStatic
    fun contains(logger: ILogger) = loggers.any { it.first == logger }

    /**
     * Representing Verbose-Logging level.
     *
     * @param message - Actual logging message.
     */
    @JvmStatic
    fun v(tag: String, message: String?) {
        loggers.forEach {
            if (it.second <= Level.VERBOSE)
                it.first.verbose(tag, "$message")
        }
    }

    /**
     * Representing Debug-Logging level.
     *
     * @param message - Actual logging message.
     */
    @JvmStatic
    fun d(tag: String, message: String?) {
        loggers.forEach {
            if (it.second <= Level.DEBUG)
                it.first.debug(tag, "$message")
        }
    }

    /**
     * Representing Information-Logging level.
     *
     * @param message - Actual logging message.
     */
    @JvmStatic
    fun i(tag: String, message: String?) {
        loggers.forEach {
            if (it.second <= Level.INFO)
                it.first.information(tag, "$message")
        }
    }

    /**
     * Representing Warning-Logging level.
     *
     * @param message - Actual logging message.
     */
    @JvmStatic
    fun w(tag: String, message: String?) {
        loggers.forEach {
            if (it.second <= Level.WARNING)
                it.first.warning(tag, "$message")
        }
    }

    /**
     * Representing Error-Logging level.
     *
     * @param message - Actual logging message.
     */
    @JvmStatic
    fun e(tag: String, message: String?) {
        loggers.forEach {
            if (it.second <= Level.ERROR)
                it.first.error(tag, "$message")
        }
    }

    @JvmStatic
    fun v(message: String?) {
        v(TAG, message)
    }

    @JvmStatic
    fun d(message: String?) {
        d(TAG, message)
    }

    @JvmStatic
    fun i(message: String?) {
        i(TAG, message)
    }

    @JvmStatic
    fun w(message: String?) {
        w(TAG, message)
    }

    @JvmStatic
    fun e(message: String?) {
        e(TAG, message)
    }

    @JvmStatic
    fun Any.logv(block: () -> String?) {
        loggers.forEach {
            if (it.second <= Level.VERBOSE) {
                it.first.verbose(TAG, "${block()}")
            }
        }
    }

    @JvmStatic
    fun Any.logd(block: () -> String?) {
        loggers.forEach {
            if (it.second <= Level.DEBUG) {
                it.first.debug(TAG, "${block()}")
            }
        }
    }

    @JvmStatic
    fun Any.logi(block: () -> String?) {
        loggers.forEach {
            if (it.second <= Level.INFO) {
                it.first.information(TAG, "${block()}")
            }
        }
    }

    @JvmStatic
    fun Any.logw(block: () -> String?) {
        loggers.forEach {
            if (it.second <= Level.WARNING) {
                it.first.warning(TAG, "${block()}")
            }
        }
    }

    @JvmStatic
    fun Any.loge(block: () -> String?) {
        loggers.forEach {
            if (it.second <= Level.ERROR) {
                it.first.error(TAG, "${block()}")
            }
        }
    }

    @JvmStatic
    fun e(throwable: Throwable?) {
        loggers.forEach {
            if (it.second <= Level.ERROR)
                it.first.exception(throwable ?: return)
        }
    }

    @JvmStatic
    fun toast(message: String?) {
        loggers.forEach {
            if (it.second <= Level.ERROR)
                it.first.toast("$message")
        }
    }

    @JvmStatic
    fun snackbar(message: String?) {
        loggers.forEach {
            if (it.second <= Level.INFO)
                it.first.snackbar("$message")
        }
    }

    @JvmStatic
    inline infix fun Any.paul(message: String?) = logv("!!!PAUL!!!! $message !!!!")

    @JvmStatic
    fun invoker(): String = Throwable().stackTrace[2].toString()

    @JvmStatic
    fun printInvoker(): Unit = logv(Throwable().stackTrace[2].toString())
}

@Deprecated(message = "Reduce release string generation: use logv{ message }", replaceWith = ReplaceWith("logv{ message }", "net.kibotu.logger.Logger.logv"))
fun Any.logv(message: String?) = Logger.v(TAG, message)

@Deprecated(message = "Reduce release string generation: use logd{ message }", replaceWith = ReplaceWith("logd{ message }", "net.kibotu.logger.Logger.logd"))
fun Any.logd(message: String?) = Logger.d(TAG, message)

@Deprecated(message = "Reduce release string generation: use logi{ message }", replaceWith = ReplaceWith("logi{ message }", "net.kibotu.logger.Logger.logi"))
fun Any.logi(message: String?) = Logger.i(TAG, message)

@Deprecated(message = "Reduce release string generation: use logw{ message }", replaceWith = ReplaceWith("logw{ message }", "net.kibotu.logger.Logger.logw"))
fun Any.logw(message: String?) = Logger.w(TAG, message)

@Deprecated(message = "Reduce release string generation: use loge{ message }", replaceWith = ReplaceWith("loge{ message }", "net.kibotu.logger.Logger.loge"))
fun Any.loge(message: String?) = Logger.e(TAG, message)