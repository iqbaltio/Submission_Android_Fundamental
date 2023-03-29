package com.iqbaltio.gituser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.iqbaltio.gituser.DetailResponse
import com.iqbaltio.gituser.viewmodel.MainViewModel
import com.iqbaltio.gituser.R
import com.iqbaltio.gituser.adapter.SectionsPagerAdapter
import com.iqbaltio.gituser.databinding.ActivityUserDetailBinding
import com.iqbaltio.gituser.viewmodel.FavoriteViewModel
import com.iqbaltio.gituser.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity(){
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: MainViewModel
    var _fav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User Detail"
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRADATA).toString()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getDetailUser(username)

        viewModel.detailUser.observe(this) { userDetail ->
            setDataList(userDetail)
        }

        viewModel.isLoading.observe(this, ::showLoading)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getFavoGak(login: String, avatar: String) {
        val username = intent.getStringExtra(EXTRADATA).toString()
        val mFavViewModel = obtainViewModel(this@UserDetailActivity)
        CoroutineScope(Dispatchers.IO).launch {
            val vCheckUser = mFavViewModel.checkUserFavorite(username)
            withContext(Dispatchers.Main) {
                if(vCheckUser.isNotEmpty()){
                    _fav = true
                    binding.fab.isSelected = true
                    binding.fab.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    _fav = false
                    binding.fab.isSelected = false
                    binding.fab.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }
        binding.fab.setOnClickListener {
            if(_fav){
                mFavViewModel.delete(login)
                _fav = false
                binding.fab.isSelected = false
                binding.fab.setImageResource(R.drawable.baseline_favorite_border_24)
            } else {
                mFavViewModel.insert(login, avatar)
                _fav = true
                binding.fab.isSelected = true
                binding.fab.setImageResource(R.drawable.baseline_favorite_24)
            }
        }
    }

    private fun obtainViewModel(detilUserActivity: UserDetailActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(detilUserActivity.application)
        return ViewModelProvider(detilUserActivity, factory)[FavoriteViewModel::class.java]
    }

    private fun setDataList(responseBody: DetailResponse) {
        binding.apply {
            tvName.text = responseBody.name
            tvUsername.text = responseBody.login
            tvFollower.text = responseBody.followers.toString() + " Followers"
            tvFollowing.text = responseBody.following.toString() + " Following"
            Glide.with(this@UserDetailActivity)
                .load(responseBody.avatarUrl)
                .circleCrop()
                .into(binding.ImgAvatar)
        }
        getFavoGak(responseBody.login.toString(),responseBody.avatarUrl.toString())
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val EXTRADATA = "extra_data"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }
}