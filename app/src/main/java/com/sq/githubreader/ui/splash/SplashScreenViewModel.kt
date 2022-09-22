package com.sq.githubreader.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sq.githubreader.network.model.ApiResult
import com.sq.githubreader.network.model.Organization
import com.sq.githubreader.network.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val _result = MutableStateFlow(ApiResult<Organization>(true, mutableListOf(), false))
    val result: StateFlow<ApiResult<Organization>>
        get() = _result

    init {
        if(githubRepository.orgCache.orgs.isEmpty()) {
            fetchOrgs(pageToken = 0, page = 0, maxPages = MAX_PAGES)
        }
    }

    /**
     * This co-routines is recursively called up to max pages
     * to allow us to fetch as many repos as possible for the list
     * based filtering.
     *
     * In the case of an excepion in any one of the intermediate pages,
     * we can still use OrgCache to retrieve results up to the page we successfully
     * ran up to
     */
    fun fetchOrgs(pageToken: Int, page: Int, maxPages: Int) {
        viewModelScope.launch {
            try {
                val result = githubRepository.getOrganizations(pageToken)
                githubRepository.orgCache.orgs.addAll(result.orgs)
                if(page < maxPages){
                    githubRepository.orgCache.orgs.sortBy { it.login.lowercase() }
                    fetchOrgs(pageToken = result.nextPageToken, page = page + 1, maxPages = 10)
                } else {
                    _result.value = ApiResult(isLoading = false, result.orgs.toMutableList(), false)
                }
            } catch (e : Exception) {
                githubRepository.orgCache.orgs.sortBy { it.login.lowercase() }
                _result.value = ApiResult(isLoading = false, mutableListOf(), true)
            }
        }
    }

    companion object {
        const val MAX_PAGES = 100
    }
}