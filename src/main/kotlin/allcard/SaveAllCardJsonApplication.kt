package allcard

import BLIZZARD_API_ID
import BLIZZARD_API_SECRET
import api.BlizzardOAuthService
import api.BlizzardService
import api.blizzardOAuthService
import api.blizzardService
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import data.RemoteHearthStoneSource
import kotlinx.coroutines.delay
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.util.*

suspend fun main() {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val source = RemoteHearthStoneSource()

    val directory = File("allCardsJson")
    if (!directory.exists()) {
        directory.mkdir()
    }

    repeat(130) {
        val page = it + 1

        val path = "allCardsJson"
        val fileName = "cardPage%03d.txt".format(page)

        if (File("$path/$fileName").exists()) {
            println("card page $page is skipped")
            return@repeat
        }

        println("called $it")
        val cardJson = source.getCard(page)
        createTextFile(
            pathToSave = "allCardsJson",
            fileName = "cardPage%03d.txt".format(page),
            content = gson.toJson(cardJson),
        )
        println("end $it")
        delay(500)
    }

    println()
    println("save complete")

    // 않이 ㅋㅋ 너무 빨라서 TooManyRequest 날리네 ㅋㅋ 걍 천천히 하나씩 기다리자

//    val exceptionHandler = CoroutineExceptionHandler { _, t ->
//        t.printStackTrace()
//    }
//
//    val getCardJobs = mutableListOf<Deferred<*>>()
//
//    repeat(127) {
//        CoroutineScope(Dispatchers.IO + exceptionHandler).async {
//            val page = it + 1
//            val cardJson = source.getCard(page)
//
//            createTextFile(
//                pathToSave = "allCardsJson",
//                fileName = "cardPage%03d.txt".format(page),
//                content = cardJson.toString(),
//            )
//            println("called $it")
//        }.also { getCardJobs.add(it) }
//    }
//
//    getCardJobs.awaitAll()
}

private fun createTextFile(pathToSave: String, fileName: String, content: String) {
    File(pathToSave, fileName).writeText(content)
}

class BlizzardOAuthRepository(
    private val blizzardOAuthService: BlizzardOAuthService,
) {
    suspend fun getAccessToken(): String {
        return blizzardOAuthService.requestOAuthToken(
            auth = getEncodedCredential(),
            grant_type = RequestBody.create(MediaType.parse("text/plain"), "client_credentials")
        ).accessToken
    }

    private fun getEncodedCredential(): String {
        val authCredentials = "$BLIZZARD_API_ID:$BLIZZARD_API_SECRET"
        val encodedCredentials = Base64.getEncoder().encodeToString(authCredentials.toByteArray())
        return "Basic $encodedCredentials"
    }
}
