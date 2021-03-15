package com.codinginflow.mvvmtodo.util

import android.widget.SearchView
import java.util.*

inline fun SearchView.onQueryTextChanged(crossinline  listener: (String)-> Unit){
    this.setOnQueryTextListener(object :SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })
}