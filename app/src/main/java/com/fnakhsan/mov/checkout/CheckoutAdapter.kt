package com.fnakhsan.mov.checkout

import android.icu.text.NumberFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fnakhsan.mov.R
import com.fnakhsan.mov.data.Checkout
import java.util.*

class CheckoutAdapter(private val checkoutList: MutableList<Checkout>) :
    RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivSeat: ImageView = view.findViewById(R.id.seat)
        val tvSeat: TextView = view.findViewById(R.id.seat_name)
        val tvPrice: TextView = view.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_row_checkout_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val checkoutListPosition = checkoutList[position]
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        holder.apply {
            ivSeat.setImageResource(R.drawable.ic_baseline_event_seat_24)
            tvSeat.text = checkoutListPosition.seat
            Log.d(TAG, (checkoutListPosition.price ?: 0).toString())
            tvPrice.text = format.format(checkoutListPosition.price?.toDouble() ?: 0)
        }
    }

    override fun getItemCount(): Int = checkoutList.size

    companion object {
        private const val TAG = "coAdapter"
    }
}