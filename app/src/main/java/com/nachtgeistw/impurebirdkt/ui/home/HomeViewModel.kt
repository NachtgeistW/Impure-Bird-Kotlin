package com.nachtgeistw.impurebirdkt.ui.home

import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import twitter4j.Status

class HomeViewModel(statusReserved: Status) : ViewModel() {
    private val _status = MutableLiveData<Status>()

    init {
        _status.value = statusReserved
    }

    var userName: LiveData<String> = Transformations.map(_status) { status -> status.user.name }
}