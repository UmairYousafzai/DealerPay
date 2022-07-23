package com.moveitech.dealerpay.network

import android.util.Log
import com.moveitech.dealerpay.application.DealerPayApplication
import com.moveitech.dealerpay.util.Constants.Companion.BASE_URL
import com.moveitech.dealerpay.util.DataStoreHelper
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


object RetrofitClient {

     val TAG= RetrofitClient::class.java.name.toString()

    private lateinit var retrofitInstance:Retrofit

    private fun getRetrofit(dataStoreHelper: DataStoreHelper): Retrofit {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.addInterceptor(getLoggingInterceptor())
        builder.addInterceptor(getAuthenticationInterceptor(dataStoreHelper))

        retrofitInstance= Retrofit.Builder()
            .client(builder.build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Log.e(TAG,"Get Retrofit fun")

        return retrofitInstance
    }

    private fun getAuthenticationInterceptor(dataStoreHelper: DataStoreHelper): Interceptor {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.IO + job)

        return Interceptor { chain ->

            Log.e(TAG,"Token=====>${DealerPayApplication.Token}")
            val originalRequest = chain.request()
            val request = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${DealerPayApplication.Token}")
                .header("Content-Type", "application/json")
                .method(originalRequest.method, originalRequest.body)
                .build()

            val response = chain.proceed(request)
            Log.d("response", response.body.toString())

            synchronized(this) {
                if (response.code == 401) {
                    try {
                        val loginResponse = retrofitInstance.create(Api::class.java).refreshToken(DealerPayApplication.refreshToken).execute()
                        if (loginResponse.isSuccessful) {
                            scope.launch {
                                loginResponse.body()?.let {
                                    DealerPayApplication.Token = it.jwtToken
                                    dataStoreHelper.saveToken(it.jwtToken)
                                    dataStoreHelper.saveRefreshToken(it.refreshToken)
                                    DealerPayApplication.refreshToken = it.refreshToken
                                }
                            }
                            val newAuthenticationRequest = originalRequest.newBuilder()
                                .addHeader("Authorization", "Bearer " + loginResponse.body()?.jwtToken)
                                .build()
                            chain.proceed(newAuthenticationRequest)
                        } else {
                            response
                        }
                    } catch (e: Exception) {
                        response
                    }
                } else {
                    response
                }
            }
        }
    }

    fun getApi(dataStoreHelper: DataStoreHelper): Api {
        return getRetrofit(dataStoreHelper).create(Api::class.java)
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}