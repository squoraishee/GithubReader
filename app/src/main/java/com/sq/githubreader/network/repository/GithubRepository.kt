package com.sq.githubreader.network.repository

import com.sq.githubreader.network.model.Organization
import com.sq.githubreader.network.model.OrganizationResponse
import com.sq.githubreader.network.model.Repo
import com.sq.githubreader.network.usecase.GetOrganizations
import com.sq.githubreader.network.usecase.GetRepos
import javax.inject.Inject
import javax.inject.Singleton

class GithubRepository @Inject constructor(
    private val getRepos: GetRepos,
    private val getOrganizations: GetOrganizations,
    val orgCache: OrgCache
) {

    suspend fun getOrganizations(pageToken: Int) : OrganizationResponse {
        return getOrganizations.execute(pageToken = pageToken)
    }

    suspend fun getRepos(orgName: String) : List<Repo> {
        return getRepos.execute(orgName)
    }
}

/**
 * A Simple In Memory Cache.  We can replace it with a Room data base if we really
 * need to speed up some of the initial load
 */
@Singleton
class OrgCache() {
    var orgs: MutableList<Organization> = mutableListOf()
}