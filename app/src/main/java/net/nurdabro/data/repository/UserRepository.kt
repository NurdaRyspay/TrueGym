package net.nurdabro.data.repository

import net.nurdabro.data.network.UserApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi

) : BaseRepository(api) {

    suspend fun getUser() = safeApiCall { api.getUser() }

    suspend fun senQr(
        user_id: Int,
        qr_string: String,
        access: String
    ) = safeApiCall {
        api.sendQr(UserApi.SendQrRequest(user_id, qr_string), "Bearer "+access)
    }


    suspend fun changePassword(
        password: String,
        password2: String,
        old_password: String,
        access: String
    ) = safeApiCall {
        api.changePassword(UserApi.ChangePasswordReques(password, password2, old_password),"Bearer "+access )
    }

}