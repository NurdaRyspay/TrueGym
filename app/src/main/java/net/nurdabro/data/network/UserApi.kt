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

    @PUT("api/change_password/{id}/")
    suspend fun changePassword(
        @Body request: ChangePasswordReques,
        @Header("Authorization") authHeader: String,
        @Path("{id}") id: Int
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