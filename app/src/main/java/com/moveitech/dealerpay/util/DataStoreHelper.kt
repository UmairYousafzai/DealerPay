package com.moveitech.dealerpay.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreHelper @Inject constructor(private val dataStore: DataStore<Preferences>) {


    val isLogin: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LOGIN] ?: false
    }


    suspend fun saveIsLogin(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGIN] = isLogin
        }
    }

    val token: Flow<String> = dataStore.data.map { preferences ->
        preferences[TOKEN] ?: ""
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    val refreshToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[REFRESH_TOKEN] ?: ""
    }

    suspend fun saveRefreshToken(token: String) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = token


        }
    }


    suspend fun clear() {
        dataStore.edit {
            it.clear()


        }
    }


    companion object {
        val IS_LOGIN = booleanPreferencesKey(name = "isLogin")
        val TOKEN = stringPreferencesKey(name = "token")
        val REFRESH_TOKEN = stringPreferencesKey(name = "refresh_token")
        const val DATA_STORE_NAME = "dealer_pay_datastore"

    }

}


