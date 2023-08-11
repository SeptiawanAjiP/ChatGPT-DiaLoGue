package com.dewakoding.dialogue.listener


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
interface NetResponseListener {
    fun onSuccess(successResponse: Any)
    fun onFailed(errorMessage: String)
}