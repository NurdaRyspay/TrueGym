package net.simplifiedcoding.data.network

import net.simplifiedcoding.data.responses.LoginResponse
import net.simplifiedcoding.data.responses.TokenResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi : BaseApi {

    @POST("token/")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}

data class LoginRequest(
    val username: String,
    val password: String
)