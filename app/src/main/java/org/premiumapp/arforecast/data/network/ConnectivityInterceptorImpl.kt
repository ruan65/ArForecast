package org.premiumapp.arforecast.data.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import org.premiumapp.arforecast.internal.ExceptionNoConnectivity

class ConnectivityInterceptorImpl(ctx: Context) : ConnectivityInterceptor {

    private val appContext = ctx.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()) throw ExceptionNoConnectivity()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return null != networkInfo && networkInfo.isConnected
    }
}