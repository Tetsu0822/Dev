package tw.com.donhi.dev

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.com.donhi.dev.network.News
import java.net.URL

class NewsViewMidel : ViewModel() {
    val TAG = NewsViewMidel::class.java.simpleName
    fun readJSON() {
        viewModelScope.launch(Dispatchers.IO) {
            val json = URL("https://donhi.com.tw/uploads/API/news2.json").readText()
            Log.d(TAG, "onCreate: $json")
            //JSON解析
//            parseJSON(json)
            //GSON類別庫(處理JSON資料)
            val news = Gson().fromJson(json, News::class.java)
            for (n in news.news) {
                Log.d(TAG, "onCreate: ${n.news_title} : ${n.file_name}")
            }
        }
    }
}