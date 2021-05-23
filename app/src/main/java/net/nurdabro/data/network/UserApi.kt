package net.nurdabro.data.network

import net.nurdabro.data.repository.ChangePasswordReques
import net.nurdabro.data.repository.ChangePasswordResponse
import net.nurdabro.data.repository.SendQrRequest
import net.nurdabro.data.repository.SendQrResponse
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

}