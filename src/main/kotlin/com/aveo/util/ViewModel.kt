package com.aveo.util

import kotlinx.coroutines.CoroutineScope

open class ViewModel {
    private lateinit var scope: CoroutineScope

    open fun init(viewModelScope: CoroutineScope) {
        this.scope = viewModelScope
    }
}