package com.mikelis.player.api.youtube.service

import com.mikelis.player.BuildConfig
import com.mikelis.player.api.youtube.service.models.search.Videos
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiService {

    @GET("search")
    fun getVideoByKeyword(
        @Query("part") part: String = "snippet",
        @Query("q") keyword: String,
        @Query ("key") key: String = BuildConfig.GoogleApiKey
    ):Observable<Videos>

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
        fun init(): YoutubeApiService {
            return Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create())
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(YoutubeApiService::class.java)

        }
    }

}