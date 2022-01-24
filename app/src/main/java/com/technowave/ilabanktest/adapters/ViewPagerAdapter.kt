package com.technowave.ilabanktest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.technowave.ilabanktest.R
import com.technowave.ilabanktest.databinding.CardviewImageBinding
import com.technowave.ilabanktest.models.GetDataResponse

class ViewPagerAdapter(
    private val context: Context,
    private val productList: List<GetDataResponse.Product>
) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val binding = CardviewImageBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .asBitmap()
            .load((productList[position].imageUrl).replace("https", "http"))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_baseline_image_not_supported_24)
            .into(holder.binding.imageView)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}