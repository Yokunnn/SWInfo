package ru.zakablukov.data.model

import com.google.gson.annotations.SerializedName

data class PeopleListResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("results")
    val people: List<PeopleResponse>,
)
