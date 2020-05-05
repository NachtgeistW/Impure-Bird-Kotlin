package com.nachtgeistw.impurebirdkt.util

import android.content.Context
import android.widget.Toast
import com.nachtgeistw.impurebirdkt.R
import com.nachtgeistw.impurebirdkt.util.ActivityCollector.finishAll
import kotlin.system.exitProcess

open class Util {
    companion object {
        fun showToastLong(context: Context, string: String) {
            Toast.makeText(context, string, Toast.LENGTH_LONG).show()
        }
        fun showToastShort(context: Context, string: String) {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
        }
    }
}