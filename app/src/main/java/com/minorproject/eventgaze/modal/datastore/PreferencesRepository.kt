package com.minorproject.eventgaze.modal.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

// Create a Context extension for DataStore
val Context.dataStore by preferencesDataStore(name = "user_prefs")

// Repository for preferences access
class PreferencesRepository(private val context: Context) {

    private val dataStore = context.dataStore

    // Define keys
    companion object {
        val SESSION_TOKEN = stringPreferencesKey("session_token")
        val USER_ROLE = stringPreferencesKey("user_role")
    }

    // Retrieve session token
    val sessionToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[SESSION_TOKEN]
    }

    // Retrieve user role
    val userRole: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_ROLE]
    }

    // Save session token
    suspend fun saveSessionToken(token: String, role: String) {
        dataStore.edit { preferences ->
            preferences[SESSION_TOKEN] = token
            preferences[USER_ROLE] = role
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferencesRepository(
        @ApplicationContext context: Context
    ): PreferencesRepository {
        return PreferencesRepository(context)
    }
}
@Module
@InstallIn(SingletonComponent::class) // Singleton scope
object ContextModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext
}
