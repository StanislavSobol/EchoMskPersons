package com.example.corelib

import androidx.appcompat.app.AppCompatDelegate

fun isNightMode() = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

fun setDefaultNightMode(checked: Boolean) {
    AppCompatDelegate.setDefaultNightMode(
        if (checked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
    )
}