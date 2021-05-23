package net.nurdabro.data.network

import net.nurdabro.data.repository.LoginRequest
import net.nurdabro.data.responses.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi : BaseApi {

    @POST("token/")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}
