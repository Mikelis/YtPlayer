package com.mikelis.player.api.youtube.service.models.search

import com.google.gson.annotations.SerializedName

data class Thumbnails (

	@SerializedName("default") val default : Default,
	@SerializedName("medium") val medium : Medium,
	@SerializedName("high") val high : High
)