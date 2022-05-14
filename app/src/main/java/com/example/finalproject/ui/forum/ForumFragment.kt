package com.example.finalproject.ui.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.ApiRet
import com.example.finalproject.CardList
import com.example.finalproject.News
import com.example.finalproject.NewsList
import com.example.finalproject.NewsList.newsList
import com.example.finalproject.databinding.FragmentForumBinding
import com.example.finalproject.databinding.NewsItemBinding
import com.example.finalproject.ui.home.CardAdapter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

class ForumFragment : Fragment() {

    private var _binding: FragmentForumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Toast.makeText(requireContext(), "我被打开了", Toast.LENGTH_SHORT).show()
        val forumViewModel =
            ViewModelProvider(this).get(ForumViewModel::class.java)

        _binding = FragmentForumBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val newsList = NewsList.newsList
//        val newsList = NewsList.newsList
//        val newsList = NewsList.newsList


        if (newsList.size == 0) {
            thread {
                try {
                    val client = OkHttpClient()
                    val request = Request.Builder()
                        .url("http://api.tianapi.com/petnews/index?key=ec443486dcada5cc53a06be355d32ea3&num=3")
                        .build()
                    println("请求API成功")
                    val response = client.newCall(request).execute()
                    val responseData = response.body?.string()
                    println("JsonData为" + responseData)
                    if (responseData != null) {
                        val jsonArray = parseJSONWithGSON(responseData)
//                    println("返回的代码为:" + apiRet.code_api)
//                    println("返回的信息为:" + apiRet.msg_api)
                        for (i in 0 until jsonArray!!.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val id = jsonObject.getString("id")
                            val ctime = jsonObject.getString("ctime")
                            val title = jsonObject.getString("title")
                            println(title)
                            val description = jsonObject.getString("description")
                            val source = jsonObject.getString("source")
                            val picUrl = jsonObject.getString("picUrl")
                            val url = jsonObject.getString("url")
                            newsList.add(
                                0,
                                News(id, ctime, title, description, source, picUrl, url)
                            )
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.newsRecyclerview.layoutManager = layoutManager
        val adapter = NewsAdapter(requireContext(), newsList)
        binding.newsRecyclerview.adapter = adapter
        println("新闻列表里面容量:")
        println(newsList.size)

//        val textView: TextView = binding.textForum
//        forumViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }


        return root
    }

    private fun parseJSONWithGSON(jsonData: String): JSONArray? {
        try {
            val json = JSONObject(jsonData)

            val code = json.getString("code")
            val msg = json.getString("msg")
            val newslist = json.getJSONArray("newslist")
            println("返回的代码为:" + code)
            println("返回的信息为:" + msg)
            println("返回的list为:" + newslist)
            return newslist
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
//        val gson = Gson()
//        val apiRet = gson.fromJson(jsonData, ApiRet::class.java)
//        println("返回的信息为(里层):" + apiRet.msg_api)
//        return apiRet
//        val typeOf = object : TypeToken<List<ApiRet>>() {}.type
//        val appList = gson.fromJson<List<ApiRet>>(jsonData, typeOf)
//        return appList
//        println("返回的msg信息:" + apiRet.msg_api)
//        val newsList = apiRet.newslist_api
//        for (news in newsList) {
//            println("newsList列表里面容量:")
////            println(newsList.size)
//            println(news.title)
//            NewsList.newsList.add(
//                0,
//                News(
//                    news.id,
//                    news.ctime,
//                    news.title,
//                    news.description,
//                    news.source,
//                    news.picUrl,
//                    news.url
//                )
//            )
//        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}