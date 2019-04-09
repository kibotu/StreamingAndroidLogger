[![Donation](https://img.shields.io/badge/by%20me%20a%20beer-brightgreen.svg)](https://www.paypal.me/janrabe/5) [![About Jan Rabe](https://img.shields.io/badge/about-me-green.svg)](https://about.me/janrabe)
# StreamingAndroidLogger [![](https://jitpack.io/v/kibotu/StreamingAndroidLogger.svg)](https://jitpack.io/#kibotu/StreamingAndroidLogger) [![](https://jitpack.io/v/kibotu/StreamingAndroidLogger/month.svg)](https://jitpack.io/#kibotu/StreamingAndroidLogger) [![Javadoc](https://img.shields.io/badge/javadoc-SNAPSHOT-green.svg)](https://jitpack.io/com/github/kibotu/StreamingAndroidLogger/master-SNAPSHOT/javadoc/index.html) [![Build Status](https://travis-ci.org/kibotu/StreamingAndroidLogger.svg?branch=master)](https://travis-ci.org/kibotu/StreamingAndroidLogger) [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)  [![Gradle Version](https://img.shields.io/badge/gradle-5.3.1-green.svg)](https://docs.gradle.org/current/release-notes) [![kotlin](https://img.shields.io/badge/kotlin-1.3.21-green.svg)](https://kotlinlang.org/) [![Licence](https://img.shields.io/badge/licence-Apache%202-blue.svg)](https://raw.githubusercontent.com/kibotu/StreamingAndroidLogger/master/LICENSE) [![androidx](https://img.shields.io/badge/androidx-brightgreen.svg)](https://developer.android.com/topic/libraries/support-library/refactor)

## Introduction

Convenient logger that adds support to having multiple different loggers and different log levels for each one of them.

## How to install

    repositories {
        maven {
            url "https://jitpack.io"
        }
    }

    dependencies {
        implementation 'com.github.kibotu:StreamingAndroidLogger:-SNAPSHOT'
    }

## How to use

1) Add a Logger

     Logger.addLogger(LogcatLogger(), Level.VERBOSE)
     Logger.addLogger(SystemLogger(), Level.VERBOSE)

2) Log with different log levels:

    logv("verbose message")
    logd("debug message")
    logi("info message")
    logw("warning message")
    loge("error message")

## Streaming Log Server

      Logger.addLogger(WebLogger(), Level.VERBOSE)
      var loggingWebServer = LoggingWebServer(port, assets)
      loggingWebServer.start()
      loggingWebServer.stop()

### Log Levels

    VERBOSE
    DEBUG
    INFO
    WARNING
    ERROR
    SILENT

### ILogger interface

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

## How to build

    graldew clean build

### CI

    gradlew clean assembleRelease test javadoc

#### Build Requirements

- JDK8
- Android Build Tools 28.0.3
- Android SDK 28

## Contributors

- [Jan Rabe](jan.rabe@kibotu.net)

### License

<pre>
Copyright 2018 Jan Rabe

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</pre>
