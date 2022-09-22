package com.sq.githubreader.network.model

import com.google.gson.annotations.SerializedName

/**
 * The next page token from the organization response is obtained from
 * the Response Header "link".  But it can also be obtained from id of the last element of each
 * page, since the ids are organized in ascending order in the responses
 */
data class OrganizationResponse (
    val orgs: List<Organization>,
    val nextPageToken: Int
)

data class Organization (
    val login: String, //name of the organization
    val id: Long?,
    @SerializedName(value = "node_id")
    val nodeID: String,
    val url: String,
    @SerializedName(value = "repos_url")
    val reposURL: String,
    val avatarURL: String,
    val description: String? = null
)