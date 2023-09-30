import com.google.gson.JsonObject
import data.LocalHearthStoneSource
import kotlinx.coroutines.runBlocking
import java.io.File

fun main() {
    runBlocking {
        val localSource = LocalHearthStoneSource()

        val allCards = (1..127).map { localSource.getCard(it) }
            .map { it.getCards() }
            .flatten()
            .let { Cards(it) }

        CardType.values().forEach { saveKeySets(allCards, it) }
    }
}

private fun saveKeySets(cards: Cards, cardType: CardType) {
    val cardTypeName = cardType.name.toLowerCase()

    val keySetsDirectory = File("cardKeySets/$cardTypeName/cases")
    if (!keySetsDirectory.exists()) {
        keySetsDirectory.mkdirs()
    }
    val filteredCards = cards.filter(cardType)
    val cardsMap = filteredCards.createCategoryCardMap()
    val cardSheets = cardsMap.map { createCardsSheet(it.key, it.value) }

    File("cardKeySets/$cardTypeName/keySets.txt").writeText(cardsMap.keys.joinToString("\n"))

    cardSheets.forEachIndexed { index, cardSheet ->
        File("cardKeySets/$cardTypeName/cases/${index}.txt").writeText(cardSheet)
    }

    val keysCountMap = filteredCards.getCategoryKeyCountMap()
    File("cardKeySets/$cardTypeName/keysCount.txt")
        .writeText(
            keysCountMap.entries
                .sortedByDescending { it.value }
                .joinToString("\n") { "${it.key}\t${it.value}" }
        )
}

fun JsonObject.getCards(): List<Card> {
    return this["cards"].asJsonArray
        .map { Card(it.asJsonObject) }
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

    fun isTypeOf(type: CardType): Boolean {
        return if (type == CardType.All) true
        else jsonObject["cardTypeId"].asInt == type.cardTypeId
    }

    override fun toString(): String {
        return category.keySet
            .map { jsonObject[it] }
            .joinToString("\t")
    }
}

data class Cards(
    val values: List<Card>
) {
    fun createCategoryCardMap(): Map<Category, Cards> {
        return values
            .groupBy { it.category }
            .map { it.key to Cards(it.value) }
            .toMap()
    }

    fun getAllCategorySet(): List<String> {
        return values.map { it.category.keySet }
            .flatten()
            .distinct()
    }

    fun getCategoryKeyCountMap(): Map<String, Int> {
        return values.map { it.category.keySet.toList() }
            .flatten()
            .groupingBy { it }
            .eachCount()
    }

    fun filter(type: CardType): Cards {
        return Cards(values.filter { it.isTypeOf(type) })
    }
}

enum class CardType(val cardTypeId: Int) {
    All(Int.MIN_VALUE), // 내가 임의로 추가
    Hero(3),
    Minion(4),
    Spell(5),
    Weapon(6),
    HeroPower(10),
    Location(39),
    Reward(40); // baconquestreward 전장용인듯
}
