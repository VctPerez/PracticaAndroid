package com.viewnext.practicaandroid.core

import android.app.Application
import com.viewnext.practicaandroid.dataretrofit.AppContainer
import com.viewnext.practicaandroid.dataretrofit.DefaultAppContainer

class PracticaAndroidApplication  : Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}