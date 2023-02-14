package com.pearl.medicap.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pearl.medicap.UI.CustomerDetailsActivity
import com.pearl.medicap.R
import com.pearl.medicap.model.CustomerMedicine

class CustomerDetailsAdapter(var context:Context,var details_list:List<CustomerMedicine>):
    Adapter<CustomerDetailsAdapter.CustomerDetails>() {


    class CustomerDetails(itemview:View):ViewHolder(itemview){
        var patient_name=itemview.findViewById<TextView>(R.id.name)
        var image=itemview.findViewById<ImageView>(R.id.imageIV)
        var pills=itemview.findViewById<TextView>(R.id.pills)
        var medicine_name=itemview.findViewById<TextView>(R.id.medicine_name)
        var cardview=itemview.findViewById<CardView>(R.id.cardview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerDetails {
        return CustomerDetails(LayoutInflater.from(context).inflate(R.layout.customer_deatails_layout,parent,false))
    }

    override fun onBindViewHolder(holder: CustomerDetails, position: Int) {
       var data=details_list[position]
        holder.image.setImageResource(data.image)
        holder.patient_name.text=data.patient_name
        holder.medicine_name.text=data.medicine_name
        holder.pills.text=data.pills
        holder.itemView.setOnClickListener {
            holder.cardview.setBackground(context.getDrawable(R.drawable.booking_box_outline))
            context.startActivity(Intent(context, CustomerDetailsActivity::class.java))
        }
    }

    override fun getItemCount()=details_list.size
}