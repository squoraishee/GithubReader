package com.sq.githubreader.network.api

import com.sq.githubreader.network.model.Organization
import com.sq.githubreader.network.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface GithubApi {

    //curl \
    //  -H "Accept: application/vnd.github+json" \
    //  -H "Authorization: Bearer <YOUR-TOKEN>" \
    // https://docs.github.com/en/rest/repos/repos
    //https://api.github.com/users/nytimes/repos
    @GET("users/{USERNAME}/repos")
    suspend fun getRepos(
        @Path(value = "USERNAME", encoded = true) username: String
    ) : List<Repo>

    //    curl \
    //    -H "Accept: application/vnd.github+json" \
    //    -H "Authorization: Bearer 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" \
    // https://docs.github.com/en/rest/orgs/orgs
    //https://api.github.com/organizations?since=5985&per_page=100
    @GET("organizations")
    suspend fun getOrganizations(
        @Query("since") since : Int,
        @Query("per_page") perPage : Int = 100
    ) : List<Organization>

    companion object{
        const val BASE_URL = "https://api.github.com/"
    }
}