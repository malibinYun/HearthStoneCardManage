package api

import BLIZZARD_API_ID
import BLIZZARD_API_SECRET
import api.response.OathResponse
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.*

interface BlizzardOathService {

    @Multipart
    @POST("oauth/token")
    fun requestOathToken(
        @Header("Authorization") auth: String = getEncodedCredential(),
        @Part("grant_type") grant_type: RequestBody = getGrantType(),
    ): Call<OathResponse>

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
