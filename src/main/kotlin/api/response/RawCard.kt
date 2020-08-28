package api.response

import com.google.gson.JsonObject

data class RawCard(
    val id: Int,
    val collectible: Int, // 0아니면 1
    val slug: String,
    val classId: Int,
    val multiClassIds: List<Int>,
    val cardTypeId: Int,
    val cardSetId: Int,
    val rarityId: Int,
    val artistName: String,
    val manaCost: Int,
    val name: String,
    val text: String,
    val image: String,
    val imageGold: String,
    val flavorText: String,
    val cropImage: String,
    val minionTypeId: Int? = null,
    val attack: Int? = null,
    val keywordIds: List<Int> = listOf(),
    val durability: Int? = null,
    val armor: Int? = null,
    val health: Int? = null,
    val parentId: Int? = null,
    val childIds: List<Int> = listOf()
) {
    constructor(jsonObject: JsonObject) : this(
        id = jsonObject["id"].asInt,
        collectible = jsonObject[""].asInt,
        slug = jsonObject[""].asString,
        classId = jsonObject[""].asInt,
        multiClassIds = jsonObject[""].asJsonArray.map { it.asInt },
        cardTypeId = jsonObject[""].asInt,
        cardSetId = jsonObject[""].asInt,
        rarityId = jsonObject[""].asInt,
        artistName = jsonObject[""].asString,
        manaCost = jsonObject[""].asInt,
        name = jsonObject[""].asString,
        text = jsonObject[""].asString,
        image = jsonObject[""].asString,
        imageGold = jsonObject[""].asString,
        flavorText = jsonObject[""].asString,
        cropImage = jsonObject[""].asString,
        minionTypeId = jsonObject[""].asInt,
        attack = jsonObject[""].asInt,
        keywordIds = jsonObject[""].asJsonArray.map { it.asInt },
        durability = jsonObject[""].asInt,
        armor = jsonObject[""].asInt,
        health = jsonObject[""].asInt,
        parentId = jsonObject[""].asInt,
        childIds = jsonObject[""].asJsonArray.map { it.asInt }
    )
}
