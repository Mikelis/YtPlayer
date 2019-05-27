package com.mikelis.player.api.youtube.service.models.search

import com.google.gson.annotations.SerializedName

data class Items (

	@SerializedName("id") val id : Id,
	@SerializedName("snippet") val snippet : Snippet
)