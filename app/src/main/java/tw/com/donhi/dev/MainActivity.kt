package tw.com.donhi.dev

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import tw.com.donhi.dev.databinding.ActivityMainBinding
import tw.com.donhi.dev.network.passSSL
import java.net.URL
import kotlin.coroutines.CoroutineContext

//Coroutines提供可繼承一個實作的CoroutineScope介面
class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val TAG = MainActivity::class.java.simpleName
    //Dispatchers 可指定Job用哪個執行緒，IO
    //1.MAIN = 主執行緒,2.IO = 讀檔、儲存、網路,3.Default,4.Unconfined = 不限定的執行緒
    val job = Job() + Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        //JSON
        //https://api.jsonserve.com/pcLzBT
        //https://donhi.com.tw/uploads/API/news2.json
        //信任SSL憑證
        passSSL.ignoreSsl()
        launch {
            val json = URL("https://donhi.com.tw/uploads/API/news2.json").readText()
            Log.d(TAG, "onCreate: $json")
            //JSON解析
            val jsonObject = JSONObject(json)
            val array = jsonObject.getJSONArray("news")
            for (i in 0..array.length()-1) {
                val nws = array.getJSONObject(i)
                val title = nws.getString("news_title")
                val fileUrl = nws.getString("file_name")
                Log.d(TAG, "onCreate: $title : $fileUrl")
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job

}