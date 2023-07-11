package com.shurish.rishabh

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CopyOnWriteArrayList

class RetriveImageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var articleList: CopyOnWriteArrayList<Article>
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private var a = 0
    private var b = 10
    private var c = 1;
    private var refreshCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrive_image)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
            val decorView: View = window.decorView
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val drawable: Drawable? = resources.getDrawable(R.drawable.background2)
            decorView.background = drawable

            recyclerView = findViewById(R.id.news_recyler)
            articleList = CopyOnWriteArrayList()
            newsAdapter = NewsAdapter(articleList, this)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = newsAdapter

            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
            progressBar = findViewById(R.id.progressBar)

            swipeRefreshLayout.setOnRefreshListener {
                refreshCount++

                val category: String = when {
                    refreshCount >= 1 && refreshCount <= 2 -> "Entertainment"
                    refreshCount >= 3 && refreshCount <= 4 -> "Technology"
                    refreshCount >= 5 && refreshCount <= 6 -> "Sports"
                    refreshCount >= 7 && refreshCount <= 8 -> "Business"
                    refreshCount >= 9 && refreshCount <= 10 -> "Health"
                    refreshCount >= 11 && refreshCount <= 12 -> "Science"
                    else -> {
                        refreshCount = 1
                        "General"
                    }
                }

                getNews(category)
            }

            getNews("All")
        }
    }

    private fun getNews(category: String) {
        articleList.clear()
        newsAdapter.notifyDataSetChanged()

        val categoryURL =
            "https://newsapi.org/v2/top-headlines?country=in&category=$category&apiKey=fc5c54af2e4441f6a667d769596a462c"
        val url =
            "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=fc5c54af2e4441f6a667d769596a462c"
        val Base_URl = "https://newsapi.org/"
        val retrofit = Retrofit.Builder()
            .baseUrl(Base_URl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitAPI = retrofit.create(RetrofitAPI::class.java)
        val call: Call<NewsModel> = if (category == "All") {
            retrofitAPI.getAllNews(url)
        } else {
            retrofitAPI.getNewsByCategory(categoryURL)
        }

        if(c==1){
            progressBar.visibility = View.VISIBLE
            c--

        }else{
            progressBar.visibility = View.INVISIBLE

        }


        call.enqueue(object : Callback<NewsModel> {
            override fun onResponse(call: Call<NewsModel>, response: Response<NewsModel>) {
                val newsModel: NewsModel? = response.body()
                val articles: List<Article>? = newsModel?.articles

                articles?.let {
                    val upperLimit = Math.min(b, articles.size)
                    for (i in a until upperLimit) {
                        articleList.add(Article(
                            articles[i].author ?: "",
                            articles[i].content ?: "",
                            articles[i].description ?: "",
                            articles[i].publishedAt ?: "",
                            articles[i].title ?: "",
                            articles[i].url ?: "",
                            articles[i].urlToImage ?: ""
                        ))
                    }
                    a = upperLimit
                    b = a + 10

                    if (a >= articles.size) {
                        a = 0
                        b = 10
                    }
                }


                newsAdapter.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<NewsModel>, t: Throwable) {
                Toast.makeText(
                    this@RetriveImageActivity,
                    "Check Your Internet Connection",
                    Toast.LENGTH_SHORT
                ).show()
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }
}
