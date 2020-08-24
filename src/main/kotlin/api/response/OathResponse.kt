package api.response

data class OathResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Long,
)
