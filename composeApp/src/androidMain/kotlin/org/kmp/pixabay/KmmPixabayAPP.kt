package org.kmp.pixabay

import android.app.Application
import android.content.Context

class KmmPixabayAPP : Application() {

    companion object {
        var instance: KmmPixabayAPP? = null
        fun getAppContext(): Context {
            return instance as Context
        }
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}