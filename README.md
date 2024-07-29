[![Donation](https://img.shields.io/badge/buy%20me%20a%20beer-brightgreen.svg)](https://www.paypal.me/janrabe/5) [![About Jan Rabe](https://img.shields.io/badge/about-me-green.svg)](https://about.me/janrabe)
# StreamingAndroidLogger [![](https://jitpack.io/v/kibotu/StreamingAndroidLogger.svg)](https://jitpack.io/#kibotu/StreamingAndroidLogger) [![](https://jitpack.io/v/kibotu/StreamingAndroidLogger/month.svg)](https://jitpack.io/#kibotu/StreamingAndroidLogger) [![Hits-of-Code](https://hitsofcode.com/github/kibotu/StreamingAndroidLogger)](https://hitsofcode.com/view/github/kibotu/StreamingAndroidLogger) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Streaming%20Android%20Logger-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7622) [![Javadoc](https://img.shields.io/badge/javadoc-SNAPSHOT-green.svg)](https://jitpack.io/com/github/kibotu/StreamingAndroidLogger/master-SNAPSHOT/javadoc/index.html) [![Build Status](https://app.travis-ci.com/kibotu/StreamingAndroidLogger.svg?branch=master)](https://app.travis-ci.com/kibotu/StreamingAndroidLogger) [![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)  [![Gradle Version](https://img.shields.io/badge/gradle-8.9-green.svg)](https://docs.gradle.org/current/release-notes) [![kotlin](https://img.shields.io/badge/kotlin-2.0.0-green.svg)](https://kotlinlang.org/) [![Licence](https://img.shields.io/badge/licence-Apache%202-blue.svg)](https://raw.githubusercontent.com/kibotu/StreamingAndroidLogger/master/LICENSE) [![androidx](https://img.shields.io/badge/androidx-brightgreen.svg)](https://developer.android.com/topic/libraries/support-library/refactor)

## Introduction

Convenient logger that adds support to having multiple different loggers and different log levels for each one of them. e.g. LogcatLogger, CrashlyticsLogger, On-Device-Weblogging, UDP (e.g. Papertrail), SystemLogging. 

![demo](demo.gif)

## How to install
```groovy
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    implementation 'com.github.kibotu:StreamingAndroidLogger:-SNAPSHOT'
}
```
## How to use

### Add a Logger

```kotlin
Logger.addLogger(LogcatLogger(), Level.VERBOSE)
Logger.addLogger(SystemLogger(), Level.VERBOSE)
Logger.addLogger(CrashlyticsLogger(), Level.WARNING)
Logger.addLogger(WebLogger(), Level.INFO)
Logger.addLogger(UDPLogger(this, "logs.papertrailapp.com", 8080), Level.INFO)
```

### Streaming Log Server

```kotlin
Logger.addLogger(WebLogger(), Level.VERBOSE)
var loggingWebServer = LoggingWebServer(port, assets)
loggingWebServer.start()
loggingWebServer.stop()
```

### Log Levels

```kotlin
VERBOSE
DEBUG
INFO
WARNING
ERROR
SILENT
```

### ILogger interface

```kotlin
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
```

## How to build

```shell
graldew clean build
```

### CI

```shell
gradlew clean assembleRelease test javadoc
````
#### Build Requirements

- JDK17
- Android Build Tools 35.0.0
- Android SDK 35


### Notes

In case you don't use the weblogger in release, add this to your build.gradle, to remove weblogging files
```groovy
release {
    […]
    aaptOptions {
        ignoreAssetsPattern "!html:!StreamingLogger:"
    }
}
``` 

### Notes

Follow me on Twitter: [@wolkenschauer](https://twitter.com/wolkenschauer)

Let me know what you think: [jan.rabe@kibotu.net](mailto:jan.rabe@kibotu.net)

Contributions welcome!

### License

<pre>
Copyright 2021 Jan Rabe

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
