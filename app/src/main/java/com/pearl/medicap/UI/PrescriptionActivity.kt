package com.pearl.medicap.UI

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.pearl.medicap.R
import com.pearl.medicap.databinding.ActivityPrescriptionBinding
import java.util.*
import kotlin.collections.ArrayList

class PrescriptionActivity : AppCompatActivity() {
    private val REQUEST_CODE_SPEECH_INPUT = 1
    var list=ArrayList<String>()
    var value=0
    var amount=0
    lateinit var binding:ActivityPrescriptionBinding
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_prescription)
      window.statusBarColor=resources.getColor(R.color.App_color)

        binding.mic.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                // on below line we are displaying error message in toast
                Toast.makeText(this, " " + e.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.medicineQuantityET.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var num=0
             if (!s?.toString().equals("null")) {
                 var sum=value+amount
                 binding.medicineSubtotalET.setText(sum.toString())
             }
                else{
                 value=s.toString().toInt()
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
           if (s!=null){

                value=s.toString().toInt()

           }
                else{
                    binding.medicineQuantityET.setText(0)
                }

            }
        })

        binding.addBtn.setOnClickListener {
            //list.clear()
            var medicine_name=binding.medicineNameET.text.toString()
            var dosage=binding.dosageET.text.toString()
            var frequency=binding.frequencyET.text.toString()
           // var time=binding.timeET.text.toString()
            var duration=binding.durationET.text.toString()
            var discount=binding.discountET.text.toString()
            var subtotal=binding.subtotalET.text.toString()
            var gst=binding.gstET.text.toString()

         /*   if(subtotal.toInt() != 0 || discount.toInt()!= 0 || gst.toInt()!= 0){
                var total=subtotal.toInt()+discount.toInt()-gst.toInt()
                binding.totalET.setText(total.toString())
            }else{
                Toast.makeText(this,"Please fill All the details",Toast.LENGTH_SHORT).show()
            }*/
          /*  val result = buildString{
              //  append(medicine_name,",",dosage,",",frequency,",",",",duration,notes)
                append(binding.medicineNameET.text.toString()+","+binding.medicineQuantityET.text.toString()
                        +","+binding.medicineDiscountET.text.toString()+","+binding.medicineSubtotalET.text.toString()+",\n"+binding.notes.text.toString())
            }*/
       /*     var timeET=findViewById<AutoCompleteTextView>(R.id.autotextview)
            var time_schedule=resources.getStringArray(R.array.time_schedule)
            var adapterr = ArrayAdapter(this,
                android.R.layout.select_dialog_item, time_schedule)
            timeET.threshold = 1
         timeET.setAdapter(adapterr)*/

            var medicine_dataList=findViewById<ListView>(R.id.medicinedatalist)
          /*  list.addAll(listOf(result))
            var adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
            medicine_dataList.adapter=adapter*/

            // amount=binding.medicinePriceET.text.toString().toInt()+binding.medicineDiscountET.text.toString().toInt()

            binding.medicineNameET.text.clear()
            binding.dosageET.text.clear()
            binding.frequencyET.text.clear()
            binding.timeET.text.clear()
            binding.durationET.text.clear()
            binding.notes.text.clear()
            binding.medicineQuantityET.text.clear()
            binding.medicineDiscountET.text.clear()
            binding.medicineSubtotalET.text.clear()

         /*   val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.customer_bill_details_dialog)
            var listView=dialog.findViewById<ListView>(R.id.list)
            list.addAll(listOf(result))
            var adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
            listView.adapter=adapter
            dialog.setCancelable(true)
            dialog.show()*/
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                binding.notes.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
    }
}}