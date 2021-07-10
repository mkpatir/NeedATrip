package com.mkpatir.needatrip.internal.extention

/**
 * Int değeri null ise 0 döner değilse kendisini döner.
 * */
fun Int?.orZero() = this ?: 0

/**
 * Int değeri verilen değerden küçükse başına sıfır ekler.
 * */
fun Int.addZeroIfSmall(value: Int = 10): String = if (this < value) "0${this}" else "$this"

/**
 * Arraylist null ise boş arraylist'e çevirir.
 * */
fun <T> ArrayList<T>?.orArrayList() = this ?: arrayListOf()