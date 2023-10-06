package com.dewakoding.dialogue

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
object LiveDataTestUtil {

    fun <T> getValue(liveData: LiveData<T>): T {
        val observer = Observer<T> { }
        liveData.observeForever(observer)
        return liveData.value!!
    }
}