package api

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface BlizzardService {

    @GET("/hearthstone/cards")
    suspend fun getCards(
        @Query("access_token") accessToken: String,
        @Query("page") page: Int,
        @Query("locale") locale: String = "ko_KR",
    ): JsonObject

    companion object {
        const val BASE_URL = "https://kr.api.blizzard.com"
    }
}
