import api.blizzardOathService
import api.response.OathResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun main() {
    blizzardOathService.requestOathToken().enqueue(object : Callback<OathResponse> {
        override fun onResponse(call: Call<OathResponse>, response: Response<OathResponse>) {
            println(response.message())
            println(response.body())
            println(response.code())
            println(response.errorBody()?.string())
            println(response.raw())
        }

        override fun onFailure(call: Call<OathResponse>, t: Throwable) {
            println(t.message)
            println(t.stackTrace.joinToString("\n"))
        }
    })

}
