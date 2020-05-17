/*
 * Copyright (c) 2020/5/14 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 *
 */

package com.nachtgeistw.impurebirdkt.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nachtgeistw.impurebirdkt.R
import com.nachtgeistw.impurebirdkt.ui.detailpage.DetailPageFragment

class DetailPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_page_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailPageFragment.newInstance())
                .commitNow()
        }
    }
}
