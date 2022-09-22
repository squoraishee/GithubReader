package com.sq.githubreader.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sq.githubreader.network.model.ApiResult
import com.sq.githubreader.network.model.Repo
import com.sq.githubreader.network.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
) : ViewModel() {

    private val _result = MutableStateFlow(ApiResult<Repo>(true, mutableListOf(), false))
    val result: StateFlow<ApiResult<Repo>>
        get() = _result

    fun fetchRepos(orgName: String) {
        viewModelScope.launch {
            try {
                val result = githubRepository.getRepos(orgName).toMutableList()
                result.sortBy {
                    -1*it.stargazersCount
                }
                val size = result.toMutableList().size
                _result.value = ApiResult(isLoading = false,
                    result.toMutableList().subList(0, if(size < 3) size else 3),
                    false)
            } catch (e : Exception) {
                _result.value = ApiResult(isLoading = false,
                    mutableListOf(),
                    true)
            }
        }
    }
}