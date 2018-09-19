[![Donation](https://img.shields.io/badge/donate-please-brightgreen.svg)](https://www.paypal.me/janrabe) [![About Jan Rabe](https://img.shields.io/badge/about-me-green.svg)](https://about.me/janrabe)
# StreamingAndroidLogger [![](https://jitpack.io/v/kibotu/StreamingAndroidLogger.svg)](https://jitpack.io/#kibotu/StreamingAndroidLogger) [![Javadoc](https://img.shields.io/badge/javadoc-SNAPSHOT-green.svg)](https://jitpack.io/com/github/kibotu/StreamingAndroidLogger/master-SNAPSHOT/javadoc/index.html) [![Build Status](https://travis-ci.org/kibotu/StreamingAndroidLogger.svg?branch=master)](https://travis-ci.org/kibotu/StreamingAndroidLogger) [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)  [![Gradle Version](https://img.shields.io/badge/gradle-4.10.1-green.svg)](https://docs.gradle.org/current/release-notes) [![Retrolambda](https://img.shields.io/badge/kotlin-1.2.70-green.svg)](https://kotlinlang.org/) [![Licence](https://img.shields.io/badge/licence-Apache%202-blue.svg)](https://raw.githubusercontent.com/kibotu/StreamingAndroidLogger/master/LICENSE) [![androidx](https://img.shields.io/badge/androidx-1.0.0--rc02-brightgreen.svg)](https://developer.android.com/topic/libraries/support-library/refactor)

## Introduction

Adds support to multiple different loggers. Handling different log levels.

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

1) Init Logger to Application#onCreate and add a Logger

       @Override
       public void onCreate() {
           super.onCreate();

           Logger.with(this);
           Logger.addLogger(new LogcatLogger(), Logger.Level.DEBUG);
       }

       @Override
       public void onTerminate() {
           super.onTerminate();

           Logger.onTerminate();
       }

2) Log with different log levels:

       Logger.v(TAG, "message")

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
- Android Build Tools 28.0.2
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
