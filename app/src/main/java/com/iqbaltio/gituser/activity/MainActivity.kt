package com.iqbaltio.gituser.activity

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.iqbaltio.gituser.ItemsItem
import com.iqbaltio.gituser.viewmodel.MainViewModel
import com.iqbaltio.gituser.R
import com.iqbaltio.gituser.adapter.UserAdapter
import com.iqbaltio.gituser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User"

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.listGitUser.observe(this) { UserResponse ->
            setUserData(UserResponse)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, 0)
        binding.rvList.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this, ::showLoading)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                mainViewModel.searchUser(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                mainViewModel.searchUser(newText)
                return false
            }
        })
        return true
    }

    private fun setUserData(userResponse: List<ItemsItem>) {
        binding.tvNouser.visibility = View.GONE
        val listUser = userResponse.map {
            ItemsItem(it.login, it.avatarUrl)
        }
        val adapter = UserAdapter(listUser)
        binding.rvList.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}