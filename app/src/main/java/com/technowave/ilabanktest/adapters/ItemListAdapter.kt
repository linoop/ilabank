package com.technowave.marksandspencer.adapters

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.technowave.ilabanktest.R
import com.technowave.ilabanktest.databinding.CardviewItemBinding
import com.technowave.ilabanktest.models.GetDataResponse

class ItemListAdapter(
    private val context: Context,
    private val itemList: List<GetDataResponse.Product>,
) :
    RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>() {

    inner class ItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CardviewItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        return ItemListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cardview_item,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.binding.title.text = itemList[position].title
        holder.binding.description.text = itemList[position].description
        Glide.with(context)
            .asBitmap()
            .load((itemList[position].imageUrl).replace("https", "http"))
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_baseline_image_not_supported_24)
            .into(holder.binding.itemImage)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}