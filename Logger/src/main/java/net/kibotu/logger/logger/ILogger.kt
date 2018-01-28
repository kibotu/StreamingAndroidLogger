package net.kibotu.logger.logger

/**
 * Logging interface for concrete device specific logger.
 *
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

interface ILogger {

    /**
     * Debug Message.
     *
     * @param tag     - Application Tag.
     * @param message - Logging message.
     */
    fun debug(tag: String, message: String)

    /**
     * Debug Message.
     *
     * @param tag     - Application Tag.
     * @param message - Logging message.
     */
    fun verbose(tag: String, message: String)

    /**
     * Information Message.
     *
     * @param tag     - Application Tag.
     * @param message - Logging message.
     */
    fun information(tag: String, message: String)

    /**
     * Warning Message.
     *
     * @param tag     - Application Tag.
     * @param message - Logging message.
     */
    fun warning(tag: String, message: String)

    /**
     * Error Message.
     *
     * @param tag     - Application Tag.
     * @param message - Logging message.
     */
    fun error(tag: String, message: String)

    /**
     * Handle caught exception.
     *
     * @param throwable - Exception
     */
    fun exception(throwable: Throwable)

    /**
     * Toast message.
     *
     * @param message - Displayed message.
     */
    fun toast(message: String)

    /**
     * Snackbar message.
     *
     * @param message - Displayed message.
     */
    fun snackbar(message: String)
}