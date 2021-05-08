package net.simplifiedcoding.data.responses

data class User(
    val id: Int,
    val username:String,
    val name: String,
    val surname: String,
    val is_superuser: Boolean
)