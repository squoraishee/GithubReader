package com.sq.githubreader.network.usecase

import com.sq.githubreader.network.api.GithubApi
import com.sq.githubreader.network.model.Repo

class GetRepos(
    private val githubApi: GithubApi
) {
    suspend fun execute(userName: String) : List<Repo> {
        return githubApi.getRepos(userName)
    }
}