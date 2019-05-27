package com.mikelis.player.api.youtube.service.models.search

import com.google.gson.annotations.SerializedName

data class Videos (

	@SerializedName("items") val items : List<Items>
)