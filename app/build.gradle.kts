plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {

    namespace = "com.library"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.library"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Libs.Compose.activity)
    implementation(Libs.Compose.navigation)
    implementation(Libs.Compose.viewModel)
    implementation(Libs.Compose.runtime)
    api(Libs.Compose.coil)

    implementation(platform(Libs.BomCompose.bomCompose))
    implementation(Libs.BomCompose.composeTooling)
    implementation(Libs.BomCompose.materialWindowSize)
//    implementation(Libs.Compose.lottie)
//    implementation(Libs.Compose.lottieCompose)

    api(Libs.BomCompose.material3)
    api(Libs.BomCompose.ui)
    implementation(Libs.BomCompose.tooling)
    implementation(Libs.BomCompose.foundation)
    implementation(Libs.BomCompose.uiUtil)


    implementation(kotlin("stdlib-jdk8", Versions.kotlin))
    implementation(Libs.Compose.runtime)
    implementation(Libs.Accompanist.permissions)
    implementation(Libs.Di.hilt)
    implementation(Libs.Compose.hilt)

    implementation(Libs.Core.retrofit)
    implementation(Libs.Core.okHttpLoggingInterceptor)
    implementation(Libs.Core.gsonConverter)
    implementation(Libs.Common.kotpref)
    implementation(Libs.Common.kotprefSupport)

    implementation(Libs.MlKit.mlKit)

    kapt(Libs.Di.compiler)
}
object Libs {

    object Di {
        const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val core = "com.google.dagger:hilt-core:${Versions.hilt}"
    }

    object Compose {
        const val activity = "androidx.activity:activity-compose:${Versions.composeActivity}"
        const val navigation =
            "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
        const val runtime = "androidx.compose.runtime:runtime"
        const val coil = "io.coil-kt:coil-compose:${Versions.coil}"
        const val hilt = "androidx.hilt:hilt-navigation-compose:${Versions.composeHiltNavigation}"
        const val lottieCompose = "com.airbnb.android:lottie-compose:${Versions.lottie}"
        const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    }

    object MlKit {
        const val mlKit = "com.google.mlkit:barcode-scanning:${Versions.mlKit}"
    }

    object BomCompose {
        const val bomCompose = "androidx.compose:compose-bom:${Versions.bomCompose}"
        const val ui = "androidx.compose.ui:ui"
        const val uiUtil = "androidx.compose.ui:ui-util"
        const val material3 = "androidx.compose.material3:material3"
        const val materialWindowSize =
            "androidx.compose.material3:material3-window-size-class"
        const val tooling = "androidx.compose.ui:ui-tooling"
        const val composeUiJUnit = "androidx.compose.ui:ui-test-junit4"
        const val composeUiManifest = "androidx.compose.ui:ui-test-manifest"
        const val composeTooling = "androidx.compose.ui:ui-tooling"
        const val foundation = "androidx.compose.foundation:foundation"
    }

    object Accompanist {
        const val permissions =
            "com.google.accompanist:accompanist-permissions:${Versions.accompanist}"
    }

    object Core {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val okHttpLoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    object Common {
        const val kotpref = "com.chibatching.kotpref:kotpref:${Versions.kotPref}"
        const val kotprefSupport = "com.chibatching.kotpref:livedata-support:${Versions.kotPref}"
    }

}

object Versions {

    const val kotPref: String = "2.13.2"
    const val kotlin = "1.9.22"

    const val hilt = "2.48"
    const val navigation = "2.7.7"
    const val gson = "2.10.1"
    const val retrofit = "2.9.0"
    const val okHttpLoggingInterceptor = "4.11.0"

    const val composeActivity = "1.8.2"
    const val composeJUnit = "1.5.3"
    const val composeNavigation = "2.5.3"
    const val composeViewModel = "2.6.0"
    const val composeHiltNavigation = "1.0.0"
    const val coil = "2.3.0"
    const val mlKit = "17.2.0"

    const val lottie = "6.4.0"
    const val bomCompose = "2023.08.00"
    const val accompanist = "0.32.0"
}