package com.mkpatir.needatrip.internal.extention

fun Int?.orZero() = this ?: 0

fun Int.addZeroIfSmall(value: Int = 10): String = if (this < value) "0${this}" else "$this"

fun <T> ArrayList<T>?.orArrayList() = this ?: arrayListOf()