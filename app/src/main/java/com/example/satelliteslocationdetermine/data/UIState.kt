package com.example.satelliteslocationdetermine.data

data class UIState<out T>(
    val loading: Boolean = false,
    val error: Throwable?= null,
    val data :T ?= null
)