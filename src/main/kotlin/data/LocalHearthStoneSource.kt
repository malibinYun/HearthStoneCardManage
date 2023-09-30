package data

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File

class LocalHearthStoneSource {
    fun getCard(page: Int): JsonObject {
        val file = File(CardJsonPath.format(page))
        return JsonParser.parseString(file.readText()).asJsonObject
    }

    companion object {
        private const val CardJsonPath = "allCardsJson/cardPage%03d.txt"
    }
}
