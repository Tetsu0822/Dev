package tw.com.donhi.dev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class NewsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, //幫助產生類別的工具
        container: ViewGroup?, //Fragment未來所在的容器
        savedInstanceState: Bundle?
    ): View? {
        //false 表未來畫面出現時Fragment是否立即附屬在畫面上
        return inflater.inflate(R.layout.layout_news, container, false)
    }
}