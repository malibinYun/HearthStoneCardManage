package data

import allcard.BlizzardOAuthRepository
import api.BlizzardService
import api.blizzardOAuthService
import api.blizzardService
import com.google.gson.JsonObject

class RemoteHearthStoneSource(
    private val blizzardService: BlizzardService,
    private val blizzardOAuthRepository: BlizzardOAuthRepository,
) {
    suspend fun getCard(page: Int): JsonObject {
        return blizzardService.getCards(blizzardOAuthRepository.getAccessToken(), page)
    }
}

fun RemoteHearthStoneSource(): RemoteHearthStoneSource = RemoteHearthStoneSource(
    blizzardService = blizzardService,
    blizzardOAuthRepository = BlizzardOAuthRepository(blizzardOAuthService),
)
