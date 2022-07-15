package br.com.zup.cafeteriasimcity.ui.favorite.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.cafeteriasimcity.databinding.ActivityFavoriteBinding
import br.com.zup.cafeteriasimcity.ui.favorite.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel: FavoriteViewModel by lazy {
        ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter(arrayListOf(), ::removeFavoriteImage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.getListFavorite()
        setUpRecyclerView()
        initObservers()
    }

    private fun setUpRecyclerView() {
        binding.rvImageCoffee.layoutManager = LinearLayoutManager(this)
        binding.rvImageCoffee.adapter = adapter
    }

    private fun initObservers() {
        viewModel.favoriteListState.observe(this) {
            adapter.updateMovieList(it.toMutableList())
        }

        viewModel.messageState.observe(this) {
            loadMessage(it)
        }
    }

    private fun loadMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun removeFavoriteImage(image: String) {
        viewModel.removeImageFavorite(image)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}