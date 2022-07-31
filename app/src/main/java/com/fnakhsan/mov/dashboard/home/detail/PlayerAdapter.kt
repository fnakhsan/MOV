package com.fnakhsan.mov.dashboard.home.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fnakhsan.mov.R
import com.fnakhsan.mov.data.Play

class PlayerAdapter(private val playerList: ArrayList<Play>) :
    RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_row_whos_played, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playerListPosition = playerList[position]
        with(holder) {
            Glide.with(ivPlayer)
                .load(playerListPosition.url)
                .apply(RequestOptions.centerCropTransform())
                .into(ivPlayer)
            tvPlayer.text = playerListPosition.nama
        }
    }

    override fun getItemCount(): Int = playerList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPlayer: ImageView = view.findViewById(R.id.circleImageView)
        val tvPlayer: TextView = view.findViewById(R.id.player_name)
    }
}