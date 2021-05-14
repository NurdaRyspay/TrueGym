package net.nurdabro.data.repository

import net.nurdabro.data.network.BaseApi
import net.nurdabro.data.network.SafeApiCall

abstract class BaseRepository(private val api: BaseApi) : SafeApiCall {

    suspend fun logout() = safeApiCall {
        api.logout()
    }
}