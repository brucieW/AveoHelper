import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    id("org.jetbrains.compose") version "1.1.0"
    id("com.squareup.sqldelight") version "1.5.3"
}

group = "com.aveo"
version = "1.0"

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

// val daggerVersion by extra("2.42")
val sqlDelightVersion by extra("1.5.3")

dependencies {
    implementation(compose.desktop.currentOs)

    // Module dependencies
//    implementation(project(":data"))
    implementation("javax.inject:javax.inject:1")

//    // Dagger : A fast dependency injector for Android and Java.
//    implementation("com.google.dagger:dagger:$daggerVersion")
//    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
//    kaptTest("com.google.dagger:dagger-compiler:$daggerVersion")

    // KODEIN
    implementation("org.kodein.di:kodein-di:7.12.0")

    /**
     * Testing Dependencies
     */
    testImplementation("org.mockito:mockito-inline:4.3.1")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    // DaggerMock
//    testImplementation("com.github.fabioCollini.daggermock:daggermock:0.8.5")
//    testImplementation("com.github.fabioCollini.daggermock:daggermock-kotlin:0.8.5")

    // Mockito Core : Mockito mock objects library core API and implementation
    testImplementation("org.mockito:mockito-core:4.3.1")

    // Expekt : An assertion library for Kotlin
    testImplementation("com.github.theapache64:expekt:1.0.0")

    // SQL DElight
    implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
    implementation("com.squareup.sqldelight:coroutines-extensions-jvm:$sqlDelightVersion")
    implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion")
    implementation("mysql:mysql-connector-java:8.0.29")

    // Excel
    implementation("org.apache.poi:poi-ooxml:5.2.2")
    implementation("org.apache.poi:poi-ooxml:5.2.2")

    // Logging
    implementation("com.ToxicBakery.logging:arbor-jvm:1.37.80")

    // JUnit

    // Kotlinx Coroutines Test : Coroutines support libraries for Kotlin
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2")
    testImplementation(compose("org.jetbrains.compose.ui:ui-test-junit4"))

    // JUnit : JUnit is a unit testing framework for Java, created by Erich Gamma and Kent Beck.
    testImplementation(kotlin("test-junit5"))
}

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.1")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

compose.desktop {
    application {
        mainClass = "presentation.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Exe)
            outputBaseDir.set(project.buildDir.resolve("distrib"))
            packageName = "Aveo"
            packageVersion = "1.0.0"

            windows {
                packageVersion = "1.0.0"
                exePackageVersion = "1.0.0"
            }
        }
    }
}

sqldelight {
    database("AveoDatabase") {
        packageName = "com.aveo.db"
        sourceFolders = listOf("sqldelight")
        dialect = "sqlite:3.25"
    }
}
