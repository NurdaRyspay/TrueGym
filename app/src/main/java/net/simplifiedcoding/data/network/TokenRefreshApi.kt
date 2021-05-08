package net.simplifiedcoding.data.network

import net.simplifiedcoding.data.responses.TokenResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenRefreshApi : BaseApi {

    @POST("api/token/refresh/")
    suspend fun refreshAccessToken(
        @Body refreshToken: RefreshRequest?
    ): TokenResponse
}

data class RefreshRequest(
    val refresh: String
)