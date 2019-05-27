package com.mikelis.player.api.youtube.service.models.search

import com.google.gson.annotations.SerializedName

data class Id (

	@SerializedName("kind") val kind : String,
	@SerializedName("videoId") val videoId : String
)