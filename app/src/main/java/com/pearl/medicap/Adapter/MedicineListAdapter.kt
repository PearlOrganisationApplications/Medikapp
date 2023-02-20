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
        var notes = itemview.findViewById<TextView>(R.id.notesTV)
        var subtotal = itemview.findViewById<TextView>(R.id.subtotalTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineHolder {
       return MedicineHolder(LayoutInflater.from(context).inflate(R.layout.medicine_list_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MedicineHolder, position: Int) {
        var data=medicineList[position]
        holder.medicine.text=data.medicine
        holder.notes.text=data.notes
        holder.subtotal.text=data.subtotal
    }

    override fun getItemCount()=medicineList.size


}
