package com.example.weather_dvt.core.network.util

import com.example.weather_dvt.core.common.utils.Constant.Companion.apiKey
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class ServiceInterceptor(private val apiKeyValue: String) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(apiKey, apiKeyValue)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}