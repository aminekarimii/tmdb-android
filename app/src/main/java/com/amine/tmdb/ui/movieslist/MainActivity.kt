package com.amine.tmdb.ui.movieslist

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amine.tmdb.data.local.model.MovieEntity
import com.amine.tmdb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var moviesAdapter: MoviesAdapter
    private val viewModel by viewModels<MovieListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesAdapter = MoviesAdapter()

        binding.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )
    }

    private fun ActivityMainBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<MovieEntity>>,
        uiActions: (UiAction) -> Unit
    ) {
        bindSearch(
            uiState = uiState,
            onQueryChanged = uiActions
        )

        bindList(
            moviesAdapter = moviesAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun searchMovies(
        searchMovies: EditText,
        recyclerView: RecyclerView,
        onQueryChanged: (UiAction.Search) -> Unit
    ) {
        searchMovies.text.trim().let {
            if (it.isNotEmpty()) {
                recyclerView.scrollToPosition(0)
                onQueryChanged(UiAction.Search(query = it.toString()))
            }
        }
    }

    private fun ActivityMainBinding.bindSearch(
        uiState: StateFlow<UiState>,
        onQueryChanged: (UiAction.Search) -> Unit
    ) {
        searchMovies.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                searchMovies(
                    searchMovies,
                    recyclerView,
                    onQueryChanged
                )
                true
            } else {
                false
            }
        }

        searchMovies.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchMovies(
                    searchMovies,
                    recyclerView,
                    onQueryChanged
                )

                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            uiState
                .map { it.query }
                .distinctUntilChanged()
                .collect {
                    searchMovies.setText(it)
                }
        }

    }

    private fun ActivityMainBinding.bindList(
        moviesAdapter: MoviesAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<MovieEntity>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
        with(recyclerView) {
            adapter = moviesAdapter

            layoutManager = GridLayoutManager(this@MainActivity, 2)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy != 0) {
                        onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
                    }
                }
            })
        }


        val notLoading = moviesAdapter.loadStateFlow
            // Only emit when REFRESH LoadState for the paging source changes.
            .distinctUntilChangedBy { it.source.refresh }
            // Only react to cases where REFRESH completes i.e., NotLoading.
            .map { it.source.refresh is LoadState.NotLoading }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        ).distinctUntilChanged()

        lifecycleScope.launchWhenCreated {
            pagingData.collectLatest {
                moviesAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) recyclerView.scrollToPosition(0)
            }
        }
    }
}
