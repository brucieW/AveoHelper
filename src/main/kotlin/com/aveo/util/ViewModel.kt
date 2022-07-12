package com.aveo.util

import kotlinx.coroutines.CoroutineScope

open class ViewModel {
    lateinit var scope: CoroutineScope

    open fun init(viewModelScope: CoroutineScope) {
        this.scope = viewModelScope
    }
}