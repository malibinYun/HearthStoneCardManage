package api.response

data class OAuthResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Long,
)
