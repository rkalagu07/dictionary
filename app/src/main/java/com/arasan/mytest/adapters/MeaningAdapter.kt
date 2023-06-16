package com.arasan.mytest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arasan.mytest.R
import com.arasan.mytest.models.Meaning

class MeaningAdapter(private val mList: List<Meaning>) :
    RecyclerView.Adapter<MeaningAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.meaning_layout_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.header.text = mList[position].partOfSpeech
        holder.meaningRV.setHasFixedSize(true)
        holder.meaningRV.adapter = DefinitionAdapter(mList[position].definitions)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val header: TextView = itemView.findViewById(R.id.meaning_header)
        val meaningRV: RecyclerView = itemView.findViewById(R.id.meaning_recyclerview)
    }
}