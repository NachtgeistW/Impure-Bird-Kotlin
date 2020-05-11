/*
 * Copyright (c) 2020/5/6 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.util

import android.app.Application
import android.content.Context
import kotlin.String

class ImpureBirdApplication : Application() {
    companion object {
        lateinit var context: Context
        const val CONSUMER_KEY: String = "3NBkXtqQHAVbEgPHXnIM09577"
        const val CONSUMER_SECRET_KEY: String = "cS5v9H3K4bbNtO3KDQ9il6eVnYZkcQfEcWeQ30KG8QWfiIwL7D"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}