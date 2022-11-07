package com.example.webexandroid.auth

import android.webkit.WebView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Log
import com.ciscowebex.androidsdk.Webex
import com.ciscowebex.androidsdk.auth.JWTAuthenticator
import com.ciscowebex.androidsdk.auth.OAuthWebViewAuthenticator
import com.ciscowebex.androidsdk.auth.TokenAuthenticator
import com.example.webexandroid.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class LoginViewModel(private val webex: Webex, private val loginRepository: LoginRepository) : BaseViewModel() {
    private val _isAuthorized = MutableLiveData<Boolean>()
    val isAuthorized: LiveData<Boolean> = _isAuthorized

    private val _isAuthorizedCached = MutableLiveData<Boolean>()
    val isAuthorizedCached: LiveData<Boolean> = _isAuthorizedCached

    private val _errorData = MutableLiveData<String>()
    val errorData : LiveData<String> = _errorData

    fun authorizeOAuth(loginWebview: WebView) {
        val oAuthAuthenticator = webex.authenticator as OAuthWebViewAuthenticator
//        //Log.e("inviewmodel","yes")
//        oAuthAuthenticator?.let { auth ->
//            Log.e("inviewmodel","auth")
            loginRepository.authorizeOAuth(loginWebview, oAuthAuthenticator).observeOn(AndroidSchedulers.mainThread()).subscribe({
                _isAuthorized.postValue(it)
            }, {
                _errorData.postValue(it.message)
            }).autoDispose()
//        } ?: run {
//            Log.e("inviewmodel","false")
//            _isAuthorized.postValue(false)
//        }
    }

    fun initialize() {
        loginRepository.initialize(webex).observeOn(AndroidSchedulers.mainThread()).subscribe({
            _isAuthorizedCached.postValue(it)
        }, {_isAuthorizedCached.postValue(false)}).autoDispose()
    }

    fun loginWithJWT(token: String) {
        val jwtAuthenticator = webex.authenticator as? JWTAuthenticator
        jwtAuthenticator?.let { auth ->
            loginRepository.loginWithJWT(token, auth).observeOn(AndroidSchedulers.mainThread()).subscribe({
                _isAuthorized.postValue(it)
            }, {
                _errorData.postValue(it.message)
            }).autoDispose()
        } ?: run {
            _isAuthorized.postValue(false)
        }
    }

    fun loginWithToken(token: String) {
        val authenticator: TokenAuthenticator = TokenAuthenticator()
        authenticator?.let { auth ->
            loginRepository.loginWithToken(token, auth).observeOn(AndroidSchedulers.mainThread()).subscribe({
                _isAuthorized.postValue(it)
            }, {
                _errorData.postValue(it.message)
            }).autoDispose()
        } ?: run {
            _isAuthorized.postValue(false)
        }
    }
}