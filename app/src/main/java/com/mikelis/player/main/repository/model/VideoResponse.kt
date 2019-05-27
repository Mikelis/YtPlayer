package com.mikelis.player.main.repository.model

import com.mikelis.player.api.youtube.service.models.search.Videos

data class VideoResponse(var videos:Videos?, var State:State = Companion.State.LOADING) {
   companion object {
       enum class State {
           LOADING, FAIL, OK
       }
   }

}