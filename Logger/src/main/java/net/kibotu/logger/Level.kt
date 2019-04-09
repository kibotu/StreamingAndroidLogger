package net.kibotu.logger

/**
 * Represents the logging levels.
 */
enum class Level(val TAG: String) {
    VERBOSE("V"),
    DEBUG("D"),
    INFO("I"),
    WARNING("W"),
    ERROR("E"),
    SILENT("")
}