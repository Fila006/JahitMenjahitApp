package com.example.jahitmenjahit.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jahitmenjahit.R
import com.example.jahitmenjahit.model.Sew

class SewListAdapter(
    private val onItemClickListener: (Sew) -> Unit
): ListAdapter<Sew, SewListAdapter.SewViewHolder>(WORDS_COMPARATOR){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SewViewHolder {
        return SewViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SewViewHolder, position: Int) {
        val sew = getItem(position)
        holder.bind(sew)
        holder.itemView.setOnClickListener {
            onItemClickListener(sew)
        }
    }

    class SewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nametextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.addresstextView)
        private val manyordersTextView: TextView = itemView.findViewById(R.id.manyorderstextView)
        private val sizeTextView: TextView = itemView.findViewById(R.id.sizetextView)

        fun bind(sew: Sew?) {
            nameTextView.text = sew?.name
            addressTextView.text = sew?.address
            manyordersTextView.text = sew?.manyorders
            sizeTextView.text = sew?.size

        }

        companion object {
            fun create(parent: ViewGroup): SewListAdapter.SewViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_sew, parent, false)
                return SewViewHolder(view)
            }
        }
    }
    companion object{
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Sew>(){
            override fun areItemsTheSame(oldItem: Sew, newItem: Sew): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Sew, newItem: Sew): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}