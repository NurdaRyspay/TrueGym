package net.nurdabro.data.repository

data class ChangePasswordReques(
        val password: String,
        val password2: String,
        val old_password: String
    )