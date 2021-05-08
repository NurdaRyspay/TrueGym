package net.simplifiedcoding.data.repository

import net.simplifiedcoding.data.network.UserApi
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

}