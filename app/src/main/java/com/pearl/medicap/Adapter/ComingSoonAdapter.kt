package com.pearl.medicap.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pearl.medicap.R
import com.pearl.medicap.model.ComingSoon

class ComingSoonAdapter(var context: Context,var cominglist:List<ComingSoon>):
    Adapter<ComingSoonAdapter.DataHolder>() {


    class DataHolder(itemview:View):ViewHolder(itemview){
        var image=itemview.findViewById<ImageView>(R.id.imageIV)
        var title=itemview.findViewById<TextView>(R.id.coming_soonTV)
        var cardBanner=itemview.findViewById<CardView>(R.id.cardbanner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(LayoutInflater.from(context).inflate(R.layout.coming_soon_layout,parent,false))
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
       var data=cominglist[position]
        holder.cardBanner.background=context.getDrawable(R.drawable.banner_background)
        holder.image.setImageResource(data.images)
        holder.title.setText(data.title)
    }

    override fun getItemCount()=cominglist.size
}