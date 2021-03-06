package com.mikelis.player.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mikelis.player.main.repository.YoutubeApiRepository
import com.mikelis.player.main.repository.model.VideoResponse
import javax.inject.Inject

class YoutubeApiViewModel @Inject constructor(private val repository: YoutubeApiRepository) : ViewModel() {
    private var searchKeyWord = "drones"
    private var lastPlayTime = 0f

    fun search(keyword: String): MutableLiveData<VideoResponse> {
        if(keyword != searchKeyWord){
            lastPlayTime = 0f
        }
        searchKeyWord = keyword
        return repository.search(keyword)
    }

    fun getKeyword(): String {
        return searchKeyWord
    }

    fun getLastPlayTime(): Float {
        return lastPlayTime
    }

    fun setLastPlayTime(time : Float) {
        lastPlayTime = time
    }

    override fun onCleared() {
        super.onCleared()
        repository.clear()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repository: YoutubeApiRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return YoutubeApiViewModel(repository) as T
        }
    }
}