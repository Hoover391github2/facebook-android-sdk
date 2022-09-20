/**
 * Copyright (c) 2014-present, Facebook, Inc. All rights reserved.
 *
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Facebook.
 *
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
}

group = "com.facebook.android"

extra["name"] = "Facebook-Bolts-Android-SDK"
extra["artifactId"] = "facebook-bolts"
extra["description"] = "Facebook Android SDK Bolts support library"
extra["url"] = "https://github.com/facebook/facebook-android-sdk"

dependencies {
    implementation(Libs.androidx_annotation)
    implementation(Libs.androidx_core_ktx)
    implementation(Libs.kotlin_stdlib)

    // Unit Tests
    testImplementation(project(":facebook-testutil"))
    testImplementation(Libs.junit)
    testImplementation(Libs.robolectric)
    testImplementation(Libs.androidx_test_core)
    testImplementation(Libs.mockito_inline)
    testImplementation(Libs.mockito_kotlin)

    testImplementation(Libs.powermock_core)
    testImplementation(Libs.powermock_api_mockito2)
    testImplementation(Libs.powermock_junit4)
    testImplementation(Libs.powermock_junit4_rule)
    testImplementation(Libs.powermock_classloading_xstream)
    testImplementation(Libs.assertj_core)

    testImplementation(Libs.android_installreferrer)
    testImplementation(Libs.kotlin_stdlib)
    testImplementation(Libs.kotlin_test_junit)
}

android {
    compileSdkVersion(Config.compileSdk)
    // The version of Jacoco used by the android gradle plugin
    jacoco {
        version = "0.8.7"
    }

    defaultConfig {
        minSdkVersion(Config.minSdk)
        targetSdkVersion(Config.targetSdk)
        consumerProguardFiles("proguard-rules.pro")
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isTestCoverageEnabled = true
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    testOptions {
        unitTests.all {
            it.jvmArgs("-XX:MaxPermSize=1024m")
            it.maxHeapSize = "4096m"
        }
    }

    sourceSets {
        named("test") {
            java.srcDir("src/test/kotlin")
        }
    }

    if (System.getenv("SANDCASTLE") == "1") {
        testOptions {
            unitTests.all {
                it.systemProperty("robolectric.dependency.repo.url", "https://maven.thefacebook.com/nexus/content/repositories/central/")
                it.systemProperty("robolectric.dependency.repo.id", "central")
                it.systemProperty("java.net.preferIPv6Addresses", "true")
                it.systemProperty("java.net.preferIPv4Stack", "false")
            }
        }
    }
}

apply(from = "${rootDir}/jacoco.gradle.kts")
apply(from = "${rootDir}/maven.gradle")
repositories {
    mavenCentral()
}