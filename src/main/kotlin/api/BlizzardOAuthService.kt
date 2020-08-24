package api

import BLIZZARD_API_ID
import BLIZZARD_API_SECRET
import api.response.OAuthResponse
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.*

interface BlizzardOAuthService {

    @Multipart
    @POST("oauth/token")
    suspend fun requestOAuthToken(
        @Header("Authorization") auth: String = getEncodedCredential(),
        @Part("grant_type") grant_type: RequestBody = getGrantType(),
    ): OAuthResponse

    private fun getEncodedCredential(): String {
        val authCredentials = String.format("%s:%s", BLIZZARD_API_ID, BLIZZARD_API_SECRET)
        val encodedCredentials = Base64.getEncoder().encodeToString(authCredentials.toByteArray())
        return "Basic $encodedCredentials"
    }

    private fun getGrantType(): RequestBody {
        val textMediaType = MediaType.parse("text/plain")
        return RequestBody.create(textMediaType, "client_credentials")
    }

    companion object {
        const val BASE_URL = "https://kr.battle.net/"
    }
}
