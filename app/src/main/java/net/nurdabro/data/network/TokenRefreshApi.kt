package net.nurdabro.data.network

import net.nurdabro.data.responses.TokenResponse
import retrofit2.http.Body
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