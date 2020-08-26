import api.BlizzardService
import api.blizzardOAuthService
import api.blizzardService
import com.google.gson.JsonObject
import kotlinx.coroutines.runBlocking
import java.io.File

fun main() {
    runBlocking {
        val categoryCardMap = createCategoryCardMap(blizzardService, getAccessToken())
        val keySets = categoryCardMap.keys

        val cardsSheets = categoryCardMap.map { createCardsSheet(it.key, it.value) }

        File("keySets.txt").writeText(keySets.joinToString("\n"))
        var count = 0
        cardsSheets.forEach { File("${count++}.txt").writeText(it) }
    }
}

suspend fun getAccessToken(): String {
    return blizzardOAuthService.requestOAuthToken().access_token
}

suspend fun createCategoryCardMap(blizzardService: BlizzardService, accessToken: String): Map<Category, Cards> {
    return getAllCards(blizzardService, accessToken).values
        .groupBy { it.category }
        .map { it.key to Cards(it.value) }
        .toMap()
}

suspend fun getAllCards(blizzardService: BlizzardService, accessToken: String): Cards {
    return (1..67).map { blizzardService.getCards(accessToken, it) }
        .map { it["cards"].asJsonArray }
        .flatMap { jsonArray -> jsonArray.map { Card(it.asJsonObject) } }
        .let { Cards(it) }
}

private fun createCardsSheet(category: Category, cards: Cards): String {
    val stringBuilder = StringBuilder().appendLine(category.toString())
    cards.values.forEach { stringBuilder.appendLine(it.toString()) }
    return stringBuilder.toString()
}

data class Category(
    val keySet: Set<String>
) {
    override fun toString(): String {
        return keySet.joinToString("\t")
    }
}

data class Card(
    val jsonObject: JsonObject
) {
    val category = Category(jsonObject.keySet())

    override fun toString(): String {
        return category.keySet
            .map { jsonObject[it] }
            .joinToString("\t")
    }
}

data class Cards(
    val values: List<Card>
)
