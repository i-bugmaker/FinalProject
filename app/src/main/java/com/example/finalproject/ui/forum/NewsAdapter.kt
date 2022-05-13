package com.example.finalproject.ui.forum

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.News
import com.example.finalproject.R
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class NewsAdapter(mContext: Context, val newsList: List<News>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsImage: ImageView = view.findViewById(R.id.news_image)
        val newsTitle: TextView = view.findViewById(R.id.news_title)
        val newsDescription: TextView = view.findViewById(R.id.news_description)
        val newsCtime: TextView = view.findViewById(R.id.news_ctime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        val news = newsList[position]
        println("newsList.size = "+ newsList.size)

        holder.newsTitle.text = news.title
        holder.newsDescription.text = news.description
        holder.newsCtime.text = news.ctime
        holder.newsImage.setImageBitmap(getURLimage(news.picUrl))

    }

    override fun getItemCount(): Int =
        newsList.size

    fun getURLimage(url: String?): Bitmap? {
        var bmp: Bitmap? = null
        try {
            val myurl = URL(url)
            // 获得连接
            val conn: HttpURLConnection = myurl.openConnection() as HttpURLConnection
            conn.setConnectTimeout(6000) //设置超时
            conn.setDoInput(true)
            conn.setUseCaches(false) //不缓存
            conn.connect()
            val inputStream: InputStream = conn.getInputStream() //获得图片的数据流
            bmp = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bmp
    }
}