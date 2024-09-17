package tw.com.donhi.dev

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.com.donhi.dev.network.New
import tw.com.donhi.dev.network.News
import java.net.URL

class NewsViewMidel : ViewModel() {
    val TAG = NewsViewMidel::class.java.simpleName
    val news = MutableLiveData<List<New>>()
    fun readJSON() {
        viewModelScope.launch(Dispatchers.IO) {
            val json = URL("https://donhi.com.tw/uploads/API/news2.json").readText()
            Log.d(TAG, "onCreate: $json")
            //JSON解析
//            parseJSON(json)
            //GSON類別庫(處理JSON資料)
            val data = Gson().fromJson(json, News::class.java)
//            for (n in data.news) {
//                Log.d(TAG, "onCreate: ${n.news_title} : ${n.file_name}")
//            }
            //SetValue設定值
            //會影響主執行緒Recycle的元件，故不能使用.value
            //news.value = data.news
            news.postValue(data.news)
        }
    }
}