package net.nurdabro.data.responses

data class LoginResponse(
    val refresh: String,
    val access: String,
    val user: User
)