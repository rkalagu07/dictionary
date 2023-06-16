package com.arasan.mytest.adapters

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arasan.mytest.R
import com.arasan.mytest.listener.ItemClickListener
import com.arasan.mytest.models.Phonetic
import kotlin.math.log

class PhoneticsAdapter(private val mList: ArrayList<Phonetic>,private val listener: ItemClickListener) :
    RecyclerView.Adapter<PhoneticsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.phonetics_layout_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        if (!TextUtils.isEmpty(item.audio)) {
            holder.countryName.text = item.audio.substringAfter("-").substringBeforeLast(".").uppercase()
            holder.phonicText.text = item.text
            holder.icon.setOnClickListener {
                listener.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val countryName: TextView = itemView.findViewById(R.id.country)
        val phonicText: TextView = itemView.findViewById(R.id.phonic)
        val icon: ImageView = itemView.findViewById(R.id.speaker_icon)
    }
}