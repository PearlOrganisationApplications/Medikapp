package com.pearl.medicap.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pearl.medicap.R
import com.pearl.medicap.model.CustomerMedicineList

class MedicineAdapter(var context:Context,var medicineList:List<CustomerMedicineList>):
    Adapter<MedicineAdapter.MedicineHolder>() {
    var mlist=ArrayList<String>()


    class MedicineHolder(itemview:View):ViewHolder(itemview){
        var medicine=itemview.findViewById<TextView>(R.id.input_medicineTV)
        var quantity = itemview.findViewById<TextView>(R.id.input_quantityTV)
        var mg = itemview.findViewById<TextView>(R.id.input_mgTV)
        var remove_btn=itemview.findViewById<ImageView>(R.id.removeBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineHolder {
        return MedicineHolder(LayoutInflater.from(context).inflate(R.layout.medicine_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MedicineHolder, position: Int) {
        var data=medicineList[position]
        holder.medicine.setText(data.Name)
        holder.quantity.setText(data.Quantity)
        holder.mg.setText(data.mg)
        holder.remove_btn.setOnClickListener {
            removeAt(position)
        }
    }

    override fun getItemCount()=medicineList.size

    private fun removeAt(position: Int) {
        mlist.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
       //notifyItemRangeChanged(position)
    }
}