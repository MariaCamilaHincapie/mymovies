package co.edu.eam.mymovies.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.edu.eam.mymovies.R
import co.edu.eam.mymovies.databinding.ActivityMainBinding
import co.edu.eam.mymovies.model.Movie
import co.edu.eam.mymovies.model.MovieDbClient
import co.edu.eam.mymovies.model.MovieDbResult
import co.edu.eam.mymovies.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieAdapter = MovieAdapter(emptyList()) { navigateTo(it) }
        binding.recycler.adapter = movieAdapter

        lifecycleScope.launch {
            val apiKey = getString(R.string.api_key)
            val popularMovies: MovieDbResult = MovieDbClient.service.listPopularMovies(apiKey)
            movieAdapter.movies = popularMovies.results
            movieAdapter.notifyDataSetChanged()

        }
    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }
}