package com.sq.githubreader.ui.main

import androidx.lifecycle.ViewModel
import com.sq.githubreader.network.model.Organization
import com.sq.githubreader.network.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    private val _queryText = MutableStateFlow("")
    val queryText: StateFlow<String>
        get() = _queryText

    private val _selectableItems = MutableStateFlow(getCachedOrgs())
    val selectableItems: StateFlow<MutableList<Organization>>
        get() = _selectableItems

    fun userTyped(query: String) {
        _selectableItems.value = getCachedOrgs().filter {
            it.login.lowercase().startsWith(query.lowercase())
        }.toMutableList()
        _queryText.value = query
    }

    fun getCachedOrgs() : MutableList<Organization> {
        return repository.orgCache.orgs
    }
}