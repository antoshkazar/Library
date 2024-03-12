package com.library.app.activity

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class LibraryApp : Application() {
    override fun onCreate() {
        super.onCreate()
//        StrictMode.enableDefaults()
//        StrictMode.setVmPolicy(
//            VmPolicy.Builder()
//                .detectLeakedClosableObjects()
//                .penaltyLog()
//                .build()
//        )
    }
}