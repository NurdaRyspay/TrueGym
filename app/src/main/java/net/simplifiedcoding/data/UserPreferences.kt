package net.simplifiedcoding.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_data_store")

class UserPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val appContext = context.applicationContext

    val accessToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }

    val refreshToken: Flow<String?>
        get() = appContext.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN]
        }

    val userId: Flow<String?>
        get() = appContext.dataStore.data.map {preference ->
            preference[USER_ID]
        }

    suspend fun saveAccessTokens(accessToken: String) {
        appContext.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }
    suspend fun saveRefreshTokens(refresh: String) {
        appContext.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = refresh
        }
    }

    suspend fun saveId(id: String){
        appContext.dataStore.edit { preference ->
            preference[USER_ID] = id
        }
    }

    suspend fun clear() {
        appContext.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh")
        private val USER_ID = stringPreferencesKey("id")
    }

}