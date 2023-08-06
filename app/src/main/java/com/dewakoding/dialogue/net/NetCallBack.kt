package com.dewakoding.dialogue.net


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
interface NetCallBack {
    fun onSuccess(successResponse: Any)
    fun onFailed(errorMessage: String)
}