package com.iqbaltio.gituser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbaltio.gituser.DetailResponse
import com.iqbaltio.gituser.ItemsItem
import com.iqbaltio.gituser.UserResponse
import com.iqbaltio.gituser.api.ApiConfig
import retrofit2.*

class MainViewModel : ViewModel() {

    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailUser: LiveData<DetailResponse> = _detailUser

    private val _listGitUser = MutableLiveData<List<ItemsItem>>()
    val listGitUser: LiveData<List<ItemsItem>> = _listGitUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        findUser("iqbal")
    }

    private fun findUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<UserResponse>{
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listGitUser.value = response.body()?.items
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun searchUser(username: String) {
        findUser(username)
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d(TAG, "Response : ${response.body()}")
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = responseBody
                    }
                } else {
                    Log.d(TAG, "onFailure: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowerUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollower(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d(TAG, "Response : ${response.body()}")
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listGitUser.value = responseBody
                    }
                } else {
                    Log.d(TAG, "onFailure: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowingUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d(TAG, "Response : ${response.body()}")
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listGitUser.value = responseBody
                    }
                } else {
                    Log.d(TAG, "onFailure: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}