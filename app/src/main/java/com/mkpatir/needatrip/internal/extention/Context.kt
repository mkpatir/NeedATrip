package com.mkpatir.needatrip.internal.extention

import android.content.Context
import android.widget.Toast

/**
 * Ekranda toast belirmesi içindir.
 * */
fun Context.showToast(text: String){
    Toast.makeText(this,text,Toast.LENGTH_LONG).show()
}