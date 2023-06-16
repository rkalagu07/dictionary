package com.arasan.mytest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arasan.mytest.R
import com.arasan.mytest.models.Definition

class DefinitionAdapter(private val mList: List<Definition>) :
    RecyclerView.Adapter<DefinitionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.definition_layout_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.definitionText.text = mList[position].definition
        if(mList[position].synonyms.isNotEmpty())
        {
            holder.synonymsText.text = mList[position].synonyms.joinToString(", ")
        }
        else{
            holder.synonymsHeader.visibility = View.GONE
            holder.synonymsText.visibility = View.GONE
        }
        if(mList[position].antonyms.isNotEmpty())
        {
            holder.antonymsText.text = mList[position].antonyms.joinToString(", ")
        }
        else{
            holder.antonymsHeader.visibility = View.GONE
            holder.antonymsText.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val definitionText: TextView = itemView.findViewById(R.id.definition_text)
        val synonymsText: TextView = itemView.findViewById(R.id.synonyms_txt)
        val synonymsHeader: TextView = itemView.findViewById(R.id.synonyms_heading)
        val antonymsText: TextView = itemView.findViewById(R.id.antonyms_text)
        val antonymsHeader: TextView = itemView.findViewById(R.id.antonyms_heading)
    }
}