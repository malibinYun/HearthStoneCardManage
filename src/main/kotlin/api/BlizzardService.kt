package api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BlizzardService {

    @GET
    fun getCards(
        @Query("access_token") accessToken: String,
        @Query("page") page: Int,
        @Query("locale") locale: String = "ko_KR",
    )

    companion object {
        const val BASE_URL = "https://kr.api.blizzard.com"
    }
}
