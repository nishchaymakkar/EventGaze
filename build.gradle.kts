import org.jetbrains.kotlin.gradle.internal.kapt.incremental.UnknownSnapshot

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id ("com.google.dagger.hilt.android") version "2.47" apply false
    alias(libs.plugins.google.gms.google.services) apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.11" apply false


}
buildscript {
    dependencies {
        classpath ("com.google.gms:google-services:4.4.2")  // or the latest version
        classpath ("com.google.firebase:firebase-crashlytics-gradle:3.0.2")  // or the latest version
    }
}
