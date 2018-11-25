package me.yasuoyuhao.yasuoyuhaopresent

import com.google.gson.Gson
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import me.yasuoyuhao.yasuoyuhaopresent.model.Weather
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

class APIService {

    public val request = Request.Builder()
    public val client = OkHttpClient()

    val apiURL = "https://samples.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=b6907d289e10d714a6e88b30761fae22"

    /**
     * 設置單例模式
     */
    companion object {
        val instance: APIService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            APIService()
        }
    }

    /**
     * 取得天氣數據
     */
    public fun fetchOpenweathermap(callback: (Weather?) -> Unit)  {
        // 使用非同步
        launch {
            try {
                // 製作 URL
                HttpUrl.parse(apiURL).let {
                    // 製作 非同步方法（網路請求）
                    val deferred: Deferred<Response> = async {
                        val request = APIService.instance.request.url(it).build()
                        return@async APIService.instance.client.newCall(request).execute()
                    }

                    // 等待網路請求
                    val response = deferred.await()
                    if (!response.isSuccessful) {
                        callback(null)
                    }

                    // 解析資料丟入模型後回傳
                    val resData = response.body()?.string()
                    val result = Gson().fromJson<Weather>(resData, Weather::class.java)
                    callback(result)
                }

            } catch (e: Exception) {
                callback(null)
            }
        }
    }

}