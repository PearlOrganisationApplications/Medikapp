package com.pearl.medicap.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pearl.medicap.R
import com.pearl.medicap.model.MedicineDetails

class MedicineDetailsAdapter(var context:Context,var medicine_list:List<MedicineDetails>):
    Adapter<MedicineDetailsAdapter.MedicineHolder>() {

    class MedicineHolder(itemview:View):ViewHolder(itemview){
        var medicineImg=itemview.findViewById<ImageView>(R.id.medicineIV)
        var pill_no=itemview.findViewById<TextView>(R.id.pill_noTV)
        var medicine_name=itemview.findViewById<TextView>(R.id.medicineName)
        var capsule_no=itemview.findViewById<TextView>(R.id.capsuleTV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineHolder {
        return MedicineHolder(LayoutInflater.from(context).inflate(R.layout.medicine_details_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MedicineHolder, position: Int) {
        var data=medicine_list[position]
        holder.medicineImg.setImageResource(data.medicine_img)
        holder.medicine_name.text=data.medicine_name
        holder.pill_no.text=data.pills
        holder.capsule_no.text=data.capsule
    }

    override fun getItemCount()=medicine_list.size
}