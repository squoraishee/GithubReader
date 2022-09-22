package com.sq.githubreader.network.usecase

import com.sq.githubreader.network.api.GithubApi
import com.sq.githubreader.network.model.OrganizationResponse

class GetOrganizations(
    private val githubApi: GithubApi
) {
    suspend fun execute(pageToken: Int): OrganizationResponse {
        val orgs = githubApi.getOrganizations(pageToken)
        return OrganizationResponse(
            orgs = orgs,
            nextPageToken = orgs.last().id?.toInt() ?: 0,
        )
    }
}