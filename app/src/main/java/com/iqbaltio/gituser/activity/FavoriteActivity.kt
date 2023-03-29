package com.iqbaltio.gituser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbaltio.gituser.ItemsItem
import com.iqbaltio.gituser.R
import com.iqbaltio.gituser.adapter.UserAdapter
import com.iqbaltio.gituser.database.Favorite
import com.iqbaltio.gituser.databinding.ActivityFavoriteBinding
import com.iqbaltio.gituser.viewmodel.FavoriteViewModel
import com.iqbaltio.gituser.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private var list = ArrayList<ItemsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite Github User"

        getFavorite()
    }

    private fun getFavorite() {
        val vModel = obtainViewModel(this@FavoriteActivity)
        vModel.getAllFavorites().observe(this){userData ->
            if(userData != null){
                binding.rvFav.visibility = View.VISIBLE
                setDataFavorite(userData)
            }
        }
    }

    private fun setDataFavorite(userData: List<Favorite>) {
        list.clear()
        for(data in userData){
            val mFollow = ItemsItem(
                data.login ?: "",
                data.avatar_url ?: ""
            )
            list.add(mFollow)
        }
        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.apply {
            rvFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            val listFavAdapter = UserAdapter(list)
            rvFav.adapter = listFavAdapter
        }
    }

    private fun obtainViewModel(favoriteActivity: FavoriteActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(favoriteActivity.application)
        return ViewModelProvider(favoriteActivity, factory)[FavoriteViewModel::class.java]
    }
}