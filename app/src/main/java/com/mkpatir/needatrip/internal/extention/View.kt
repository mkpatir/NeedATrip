package com.mkpatir.needatrip.internal.extention

import android.view.View

fun setOnClickListeners(views: Array<View>, onClickListener: () -> Unit){
    views.forEach {
        it.setOnClickListener {
            onClickListener()
        }
    }
}