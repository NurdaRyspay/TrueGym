package net.nurdabro.data.repository

import net.nurdabro.data.UserPreferences
import net.nurdabro.data.network.AuthApi
import net.nurdabro.data.network.LoginRequest
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository(api) {

    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(LoginRequest(email, password))
    }

    suspend fun saveAccessTokens(accessToken: String) {
        preferences.saveAccessTokens(accessToken)
    }

    suspend fun saveRefreshToken(refreshToken: String){
        preferences.saveRefreshTokens(refreshToken)
    }

    suspend fun saveId(userId: String){
        preferences.saveId(userId)
    }

}