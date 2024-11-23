plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)
    id("com.google.devtools.ksp")
    id ("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}

android {
    namespace = "com.minorproject.eventgaze"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.minorproject.eventgaze"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)

    implementation(libs.material)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.palette.ktx)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //dagger
    implementation ("com.google.dagger:hilt-android:2.49")

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    ksp ("com.google.dagger:hilt-compiler:2.48")

    implementation("com.google.firebase:firebase-crashlytics:19.2.1")
    implementation("com.google.android.gms:play-services-auth:21.2.0")// or the latest version

    implementation( "androidx.compose.material:material:1.7.4")
    implementation("androidx.compose.material:material-icons-extended:1.7.4")
    implementation(kotlin("script-runtime"))

    implementation( "androidx.compose.ui:ui-graphics:1.7.4" )// Or the latest version
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation( "androidx.palette:palette-ktx:1.0.0")

    implementation(libs.androidx.compose.animation)
    implementation("androidx.core:core-splashscreen:1.0.1")
    // Retrofit with Kotlin serialization Converter

    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("io.coil-kt.coil3:coil-compose:3.0.0-rc01")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
// Retrofit with Scalar Converter
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    implementation("org.mongodb:mongodb-driver-sync:4.8.0")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.3")
    implementation("com.google.accompanist:accompanist-permissions:0.30.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.30.0")

        // For Play Integrity (Recommended)
        implementation ("com.google.firebase:firebase-appcheck-playintegrity:17.0.1")
    implementation("dev.chrisbanes.haze:haze-jetpack-compose:0.4.1")
    implementation("com.exyte:animated-navigation-bar:1.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    implementation( "androidx.compose.animation:animation:1.7.5")
    implementation ("androidx.camera:camera-core:1.1.0")
    implementation ("androidx.camera:camera-camera2:1.1.0")
    implementation ("androidx.camera:camera-lifecycle:1.1.0")
    implementation ("androidx.camera:camera-view:1.1.0")
    implementation ("com.google.guava:guava:31.1-android")
    implementation("com.google.accompanist:accompanist-pager:0.30.0")

    implementation ("androidx.datastore:datastore-preferences:1.0.0")

}