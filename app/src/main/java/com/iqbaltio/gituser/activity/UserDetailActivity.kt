package com.iqbaltio.gituser.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User Detail"
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRADATA).toString()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getDetailUser(username)

        viewModel.detailUser.observe(this) { userDetail ->
            setDataList(userDetail)
        }

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