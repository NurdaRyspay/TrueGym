package net.nurdabro.data.repository

data class SendQrRequest(
        val user_id: Int,
        val qr_string: String
    )