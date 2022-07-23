package com.moveitech.dealerpay.application

import android.app.Application
import com.moveitech.dealerpay.util.DataStoreHelper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@HiltAndroidApp
class DealerPayApplication : Application() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    @Inject
    lateinit var dataStoreHelper: DataStoreHelper


    companion object{
         var refreshToken:String=""
         var Token:String=""



    }
    override fun onCreate() {
        super.onCreate()


        scope.launch {
            dataStoreHelper.token.collect{
                Token=it
            }
        }
        scope.launch {
            dataStoreHelper.refreshToken.collect{
                refreshToken=it
            }
              }

    }



}