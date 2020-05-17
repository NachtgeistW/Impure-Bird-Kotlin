/*
 * Copyright (c) 2020/5/16 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nachtgeistw.impurebirdkt.R
import com.nachtgeistw.impurebirdkt.ui.pic.PicFragment

class PicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pic_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PicFragment.newInstance())
                .commitNow()
        }
    }
}
