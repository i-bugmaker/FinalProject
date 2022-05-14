package com.example.finalproject.ui.forum

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.HomePage
import com.example.finalproject.News
import com.example.finalproject.R


class NewsAdapter(mContext: Context, val newsList: List<News>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    val myContext = mContext

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsImage: ImageView = view.findViewById(R.id.news_image)
        val newsTitle: TextView = view.findViewById(R.id.news_title)
        val newsDescription: TextView = view.findViewById(R.id.news_description)
        val newsCtime: TextView = view.findViewById(R.id.news_ctime)
        val newsLayout: LinearLayout = view.findViewById(R.id.news_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
//        val viewHolder = ViewHolder(view)
//        viewHolder.itemView.setOnClickListener {
//            val position = viewHolder.bindingAdapterPosition
//            val news = newsList[position]
//            Toast.makeText(
//                parent.context, "you clicked view ${news.title}",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        viewHolder.newsImage.setOnClickListener {
//            val position = viewHolder.bindingAdapterPosition
//            println("the position in [onCreateViewHolder]  is : " + position)
//            println("newsList len in [onCreateViewHolder] is : " + newsList.size)
//            val news = newsList[position]
//            Toast.makeText(
//                parent.context, "you clicked image ${news.title}",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        val news = newsList[position]
        println("the position in [onBindViewHolder]  is : " + position)
        println("newsList len in [onBindViewHolder] is : " + newsList.size)

        holder.newsTitle.text = news.title
        holder.newsDescription.text = news.description
        holder.newsCtime.text = news.ctime
        println(news.picUrl)
//        holder.newsImage.setImageBitmap(getURLimage(news.picUrl))
        Glide.with(myContext).load(news.picUrl).into(holder.newsImage)
        holder.newsLayout.setOnClickListener(View.OnClickListener {
            val news = newsList[position]
            Toast.makeText(
                myContext, "you clicked newsLayout ${news.title}", Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(myContext, NewsDetailActivity::class.java)
            intent.putExtra("url", news.url)
//            startActivity(intent)
            startActivity(myContext, intent, null)
        })
    }

    override fun getItemCount(): Int =
        newsList.size

//    fun getURLimage(url: String?): Bitmap? {
//        var bmp: Bitmap? = null
//        try {
//            val myurl = URL(url)
//            // 获得连接
//            val conn: HttpURLConnection = myurl.openConnection() as HttpURLConnection
//            conn.setConnectTimeout(6000) //设置超时
//            conn.setDoInput(true)
//            conn.setUseCaches(false) //不缓存
//            conn.connect()
//            val inputStream: InputStream = conn.getInputStream() //获得图片的数据流
//            bmp = BitmapFactory.decodeStream(inputStream)
//            inputStream.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return bmp
//    }
}