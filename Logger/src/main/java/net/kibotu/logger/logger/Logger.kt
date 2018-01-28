package net.kibotu.logger.logger

import android.app.Application
import net.kibotu.ContextHelper
import java.util.*


/**
 * Static logging facet for concrete device specific logger.
 * Depending on Logging level it shows only higher prioritized logs.
 * For instance: if the Logging Level is set to info, no debug messages will be shown, etc.
 *
 *
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
object Logger {

    /**
     * Logging level.
     */
    private var logLevel = Level.DEBUG
    /**
     * Concrete Logger.
     */
    private val loggers = ArrayList<ILogger>()

    private var TAG = Logger::class.java.simpleName

    @JvmStatic
    fun with(context: Application) {
        ContextHelper.with(context)
        setDefaultTag(tag = context.javaClass.simpleName)
    }

    @JvmStatic
    fun onTerminate() {
        ContextHelper.onTerminate()
    }

    @JvmStatic
    fun setDefaultTag(tag: String) {
        Logger.TAG = tag
    }

    /**
     * Constructor.
     *
     * @param logger - Concrete Logger.
     */
    @JvmStatic
    fun addLogger(logger: ILogger) {
        Logger.loggers.add(logger)
    }

    /**
     * Constructor.
     *
     * @param logger - Concrete Logger.
     * @param level  - Logging level.
     */
    @JvmStatic
    fun addLogger(logger: ILogger, level: Level) {
        addLogger(logger)
        Logger.logLevel = level
    }

    /**
     * Gets Logging level.
     *
     * @return Currently set logging level.
     */
    @JvmStatic
    fun getLogLevel(): Level {
        return logLevel
    }

    /**
     * Sets new Logging level.
     *
     * @param logLevel new logging level.
     */
    @JvmStatic
    fun setLogLevel(logLevel: Level) {
        if (isEmpty(loggers))
            addLogger(LogcatLogger())
        else
            Logger.logLevel = logLevel
    }

    /**
     * Checks against logging level.
     *
     * @param level - Defined logging level.
     * @return true if logging is allowed.
     */
    private fun allowLogging(level: Level): Boolean {
        if (isEmpty(loggers))
            addLogger(LogcatLogger(), Level.VERBOSE)
        return logLevel.compareTo(level) <= 0
    }

    @JvmStatic
    fun allowLogging(): Boolean {
        return allowLogging(logLevel)
    }

    /**
     * Representing Verbose-Logging level.
     *
     * @param message - Actual logging message.
     */
    @JvmStatic
    fun v(tag: String, message: String?) {
        if (allowLogging(Level.VERBOSE))
            for (logger in loggers)
                logger.verbose(tag, "" + message!!)
    }

    /**
     * Representing Debug-Logging level.
     *
     * @param message - Actual logging message.
     */
    @JvmStatic
    fun d(tag: String, message: String?) {
        if (allowLogging(Level.DEBUG))
            for (logger in loggers) logger.debug(tag, "" + message!!)
    }

    /**
     * Representing Information-Logging level.
     *
     * @param message - Actual logging message.
     */
    @JvmStatic
    fun i(tag: String, message: String?) {
        if (allowLogging(Level.INFO))
            for (logger in loggers)
                logger.information(tag, "" + message!!)
    }

    /**
     * Representing Warning-Logging level.
     *
     * @param message - Actual logging message.
     */
    @JvmStatic
    fun w(tag: String, message: String?) {
        if (allowLogging(Level.WARNING))
            for (logger in loggers)
                logger.warning(tag, "" + message!!)
    }

    /**
     * Representing Error-Logging level.
     *
     * @param message - Actual logging message.
     */
    @JvmStatic
    fun e(tag: String, message: String?) {
        if (allowLogging(Level.ERROR))
            for (logger in loggers)
                logger.error(tag, "" + message!!)
    }

    /**
     * Representing Error logging of throwable.
     *
     * @param throwable - Actual throwable.
     */
    @JvmStatic
    fun printStackTrace(throwable: Throwable?) {
        if (throwable == null) return
        if (allowLogging(Level.ERROR)) throwable.printStackTrace()
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

    fun e(message: String?) {
        e(TAG, message)
    }

    @JvmStatic
    fun e(throwable: Throwable?) {
        if (allowLogging(Level.ERROR) && throwable != null)
            for (logger in loggers)
                logger.exception(throwable)
    }

    @JvmStatic
    fun toast(message: String?) {
        if (allowLogging(Level.INFO))
            for (logger in loggers)
                logger.toast(message!!)
    }

    @JvmStatic
    fun snackbar(message: String?) {
        if (allowLogging(Level.INFO))
            for (logger in loggers)
                logger.snackbar(message!!)
    }

    /**
     * Represents the logging levels.
     */
    enum class Level private constructor(val TAG: String) {
        VERBOSE("V"),
        DEBUG("D"),
        INFO("I"),
        WARNING("W"),
        ERROR("E"),
        SILENT("")
    }

    private fun isEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }
}