package net.simplifiedcoding.data.repository

import net.simplifiedcoding.data.UserPreferences
import net.simplifiedcoding.data.network.AuthApi
import net.simplifiedcoding.data.network.LoginRequest
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