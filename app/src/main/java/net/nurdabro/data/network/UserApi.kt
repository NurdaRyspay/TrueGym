package net.nurdabro.data.network

import net.nurdabro.data.responses.LoginResponse
import retrofit2.http.*

interface UserApi : BaseApi{
    @GET("user")
    suspend fun getUser(): LoginResponse


    @POST("api/send_qr/")
    suspend fun sendQr(
        @Body request: SendQrRequest,
        @Header("Authorization") authHeader: String
    ): SendQrResponse

    data class SendQrRequest(
        val user_id: Int,
        val qr_string: String
    )

    data class SendQrResponse(
        val detail: String
    )
}