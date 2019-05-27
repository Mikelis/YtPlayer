package com.mikelis.player.main.repository

import androidx.lifecycle.MutableLiveData
import com.mikelis.player.api.youtube.service.YoutubeApiService
import com.mikelis.player.main.repository.model.VideoResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class YoutubeApiRepository {
    val subs: CompositeDisposable = CompositeDisposable()
    private val service by lazy {
        YoutubeApiService.init()
    }

    fun search(keyword: String): MutableLiveData<VideoResponse> {
        val data = MutableLiveData<VideoResponse>()
        data.value = VideoResponse(null, VideoResponse.Companion.State.LOADING)
        subs.add(
            service.getVideoByKeyword(keyword = keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.items.isEmpty()){
                        data.value = VideoResponse(it, VideoResponse.Companion.State.EMPTY)
                    }
                    else{
                        data.value = VideoResponse(it, VideoResponse.Companion.State.OK)
                    }

                }, {
                    data.value = VideoResponse(null, VideoResponse.Companion.State.FAIL)
                })
        )
        return data
    }

    fun clear() {
        subs.clear()
    }
}