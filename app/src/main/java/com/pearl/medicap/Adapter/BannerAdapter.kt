package com.pearl.medicap.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pearl.medicap.R

class BannerAdapter(var context: Context,var bannerList:List<Int>):
    Adapter<BannerAdapter.DataHolder>() {

    class DataHolder(itemview:View):ViewHolder(itemview){
        var banner_img=itemview.findViewById<ImageView>(R.id.banner_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(LayoutInflater.from(context).inflate(R.layout.banner_layout,parent,false))
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
       var data=bannerList[position]
        holder.banner_img.setImageResource(data)
    }

    override fun getItemCount()=bannerList.size
}