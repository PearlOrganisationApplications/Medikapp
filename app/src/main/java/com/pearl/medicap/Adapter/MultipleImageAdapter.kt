package com.pearl.medicap.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pearl.medicap.R
import com.pearl.medicap.model.MultipleImage

class MultipleImageAdapter(var context:Context,var imagelist:List<MultipleImage>):
    Adapter<MultipleImageAdapter.ImageHolder>() {


    class ImageHolder(itemview:View):ViewHolder(itemview){
        var images=itemview.findViewById<ImageView>(R.id.imageIV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
       return ImageHolder(LayoutInflater.from(context).inflate(R.layout.multiple_image_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
       var data=imagelist[position]
        holder.images.setImageURI(data.image)
    }

    override fun getItemCount()=imagelist.size
}