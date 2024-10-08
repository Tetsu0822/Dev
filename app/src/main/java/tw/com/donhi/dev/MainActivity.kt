package tw.com.donhi.dev

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import tw.com.donhi.dev.databinding.ActivityMainBinding
import tw.com.donhi.dev.network.News
import tw.com.donhi.dev.network.passSSL
import java.net.URL
import kotlin.coroutines.CoroutineContext

//Coroutines提供可繼承一個實作的CoroutineScope介面
class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var viewModel: NewsViewMidel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val TAG = MainActivity::class.java.simpleName
    //設定取得權限
    val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { success ->
        if (success) {
            takePhoto()
        } else {
            Snackbar.make(binding.root, "Denied", Snackbar.LENGTH_LONG).show()
        }
    }
    //Dispatchers 可指定Job用哪個執行緒，IO
    //1.MAIN = 主執行緒,2.IO = 讀檔、儲存、網路,3.Default,4.Unconfined = 不限定的執行緒
    val job = Job() + Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //實作viewModel
        viewModel = ViewModelProvider(this).get(NewsViewMidel::class.java)

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
        viewModel.readJSON()

        //List 清單
        //names
        val names = listOf<String>("Aaren", "Aba", "Jack", "Jane", "Tetsu", "Win", "Carol",
            "John", "Dona", "lulu", "Jackie", "Ellen", "Gorge", "Gary", "Ruby", "Hank", "Joe",
            "Jenifer", "Sandy", "Sunny", "Abbe", "Cassy", "Dale", "Ella", "Hebe", "Salina")
        val recyler = binding.contentView.recycler
        //需設定三項
        recyler.setHasFixedSize(true) //1.是否有固定大小
        recyler.layoutManager = LinearLayoutManager(this) //2.設定裡面的Layout
        //3.重要元件 adapter,畫面資料的定義，並新增『名稱』Adapter
        //recyler.adapter = NameAdapter(names)
        viewModel.news.observe(this) { news ->
            recyler.adapter = NewAdapter(news)
        }
        //Spinner
        val nameAdapter = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, names)
        //設定下拉時的每一列
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = binding.contentView.spinner
        spinner.adapter = nameAdapter
        //設定下拉選單的預設顯示項目
        spinner.prompt = "Select Name"



    }

    private fun parseJSON(json: String) {
        val jsonObject = JSONObject(json)
        val array = jsonObject.getJSONArray("news")
        for (i in 0..array.length() - 1) {
            val nws = array.getJSONObject(i)
            val title = nws.getString("news_title")
            val fileUrl = nws.getString("file_name")
            Log.d(TAG, "onCreate: $title : $fileUrl")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //使用者按下選單時，執行的方法
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_camera -> {
                //是否已取得使用者權限
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto()
                } else {
                    requestPermission.launch(Manifest.permission.CAMERA)
                }
                true //表是否已處理此功能所需的程式(consume事件)
            }
            R.id.action_test -> {
                Intent(this, NewsActivity::class.java).also {
                    startActivity(it)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    override val coroutineContext: CoroutineContext
        get() = job

}