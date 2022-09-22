package com.sq.githubreader.network.model

/**
 * An api result fitting the types of items we're obtaining,
 * namely organization lists and repos. A more complex api result
 * would be used in the case of a more complex response
 */
data class ApiResult<T>(
    val isLoading: Boolean,
    var items: MutableList<T>,
    var isError: Boolean
)