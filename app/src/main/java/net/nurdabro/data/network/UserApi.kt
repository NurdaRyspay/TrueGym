package net.nurdabro.data.network

import net.nurdabro.data.responses.LoginResponse
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

    @PUT("api/change_password/4/")
    suspend fun changePassword(
        @Body request: ChangePasswordReques,
        @Header("Authorization") authHeader: String
    ): ChangePasswordResponse

    data class ChangePasswordReques(
        val password: String,
        val password2: String,
        val old_password: String
    )

    data class ChangePasswordResponse(
        val changed: Boolean
    )

    data class SendQrRequest(
        val user_id: Int,
        val qr_string: String
    )

    data class SendQrResponse(
        val detail: String
    )
}