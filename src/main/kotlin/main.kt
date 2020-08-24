import api.blizzardOAuthService
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val response = blizzardOAuthService.requestOAuthToken()
        println(response)
    }
}




