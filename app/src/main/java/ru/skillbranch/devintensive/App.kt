package ru.skillbranch.devintensive

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class App : Application() {

    companion object {
        private var INSTANCE: App? = null

        fun applicationContext(): Context {
            return INSTANCE!!.applicationContext
        }
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()

        PreferencesRepository.getAppTheme().also {
            AppCompatDelegate.setDefaultNightMode(it)
        }
    }
}
