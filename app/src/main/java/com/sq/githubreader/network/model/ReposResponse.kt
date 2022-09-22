package com.sq.githubreader.network.model

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class Repo (
    val id: Long,
    @SerializedName(value = "html_url")
    val htmlURL: String,
    val description: String,
    val name: String,
    @SerializedName(value = "full_name")
    val fullName: String,
    @SerializedName(value = "stargazers_count")
    val stargazersCount: Long //stars, serving as the popularity index
)