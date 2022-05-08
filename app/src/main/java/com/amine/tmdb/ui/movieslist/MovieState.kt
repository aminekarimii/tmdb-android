package com.amine.tmdb.ui.movieslist

const val LAST_QUERY_SCROLLED: String = "last_query_scrolled"
const val LAST_SEARCH_QUERY: String = "last_search_query"
const val DEFAULT_QUERY = ""

sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Scroll(val currentQuery: String) : UiAction()
}

data class UiState(
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false
)