package tw.com.donhi.dev.network

data class News(
    val news: List<New>
)

data class New(
    val file_name: String,
    val id: Int,
    val news_post_date: String,
    val news_sort_title: String,
    val news_title: String
)