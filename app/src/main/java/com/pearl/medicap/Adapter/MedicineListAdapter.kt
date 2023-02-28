package com.pearl.medicap.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pearl.medicap.R
import com.pearl.medicap.model.MedicineListDAta

class MedicineListAdapter (var context: Context, var medicineList:List<MedicineListDAta>):
    RecyclerView.Adapter<MedicineListAdapter.MedicineHolder>() {


    class MedicineHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var medicine = itemview.findViewById<TextView>(R.id.medicine)
        var quantity = itemview.findViewById<TextView>(R.id.quantity)
        var discount = itemview.findViewById<TextView>(R.id.discount)
        var priceperunit = itemview.findViewById<TextView>(R.id.price)
        var notes = itemview.findViewById<TextView>(R.id.notesTV)
        var subtotal = itemview.findViewById<TextView>(R.id.subtotalTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineHolder {
       return MedicineHolder(LayoutInflater.from(context).inflate(R.layout.medicine_list_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MedicineHolder, position: Int) {
        var data=medicineList[position]
        holder.medicine.text=data.medicine
        holder.quantity.text = data.quantity
        holder.discount.text = data.discount
        holder.priceperunit.text = data.pricePerUnit
        holder.notes.text=data.notes
        holder.subtotal.text=data.subtotal
    }

    override fun getItemCount()=medicineList.size


}
