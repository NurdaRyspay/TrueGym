package net.simplifiedcoding.data.network

import net.simplifiedcoding.data.responses.LoginResponse
import okhttp3.ResponseBody
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