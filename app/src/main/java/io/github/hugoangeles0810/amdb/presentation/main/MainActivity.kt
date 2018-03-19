package io.github.hugoangeles0810.amdb.presentation.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import io.github.hugoangeles0810.amdb.DaggerAppComponent
import io.github.hugoangeles0810.amdb.R
import io.github.hugoangeles0810.amdb.domain.entities.Movie
import io.github.hugoangeles0810.amdb.presentation.common.BaseActivity
import io.github.hugoangeles0810.amdb.presentation.common.DataState
import io.github.hugoangeles0810.amdb.presentation.common.getViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerAppComponent.builder().build().injectTo(this)

        setSupportActionBar(toolbar)

        val adapter = RVMoviesAdapter()
        rvMovies.adapter = adapter

        moviesViewModel = getViewModel()
        moviesViewModel.moviesDataState.observe(this, Observer {
            when (it) {
                is DataState.Loading -> {
                    adapter.data = emptyList()
                    progressBar.visibility = View.VISIBLE
                }
                is DataState.Error -> {
                    adapter.data = emptyList()
                    progressBar.visibility = View.GONE
                }
                is DataState.Complete<List<Movie>> -> {
                    adapter.data = it.result
                    progressBar.visibility = View.GONE
                }
            }
        })

        moviesViewModel.loadMovies()

    }
}
