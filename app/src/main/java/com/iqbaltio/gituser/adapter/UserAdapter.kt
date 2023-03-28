package com.iqbaltio.gituser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iqbaltio.gituser.ItemsItem
import com.iqbaltio.gituser.R
import com.iqbaltio.gituser.activity.UserDetailActivity

class UserAdapter (private val listUser: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.user_list, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = listUser[position]
        viewHolder.apply {
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(imgUser)
            tvNama.text = user.login
            viewHolder.itemView.setOnClickListener{
                val acontext = viewHolder.itemView.context
                val sendUsername = user.login
                val pindah = Intent(acontext, UserDetailActivity::class.java)
                pindah.putExtra(UserDetailActivity.EXTRADATA, sendUsername)
                acontext.startActivity(pindah)
            }
        }
    }

    override fun getItemCount() = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgUser: ImageView = view.findViewById(R.id.imgUser)
        val tvNama: TextView = view.findViewById(R.id.tvNama)
    }
}