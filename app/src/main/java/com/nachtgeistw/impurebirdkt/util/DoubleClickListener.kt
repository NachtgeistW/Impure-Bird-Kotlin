/*
 * Copyright (c) 2020/5/13 (YYYY/MM/DD)
 * Created by NachtgeistW.
 * This file is used to:
 * The interface of double click event
 */

package com.nachtgeistw.impurebirdkt.util

import android.view.View

interface DoubleClickListener {
    fun OnSingleClick(v: View?)
    fun OnDoubleClick(v: View?)
}