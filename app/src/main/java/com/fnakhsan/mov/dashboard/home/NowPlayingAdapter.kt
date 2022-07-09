package com.fnakhsan.mov.dashboard.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fnakhsan.mov.R
import com.fnakhsan.mov.data.Film

class NowPlayingAdapter(private val filmList: ArrayList<Film>) :
    RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(film: Film)
    }

    fun setOnItemCLickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_row_now_playing, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filmListPosition = filmList[position]
        with(holder) {
            Glide.with(tvPoster)
                .load(filmListPosition.poster)
                .apply(RequestOptions().override(136, 192))
                .into(tvPoster)
            title.text = filmListPosition.judul
            genre.text = filmListPosition.genre
            rate.text = filmListPosition.rating
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(filmListPosition) }
        }
    }

    override fun getItemCount(): Int = filmList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPoster = view.findViewById<ImageView>(R.id.iv_poster)
        val title = view.findViewById<TextView>(R.id.tv_title)
        val genre = view.findViewById<TextView>(R.id.tv_genre)
        val rate = view.findViewById<TextView>(R.id.tv_rate)
    }

}