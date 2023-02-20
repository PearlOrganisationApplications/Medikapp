package com.pearl.medicap.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pearl.medicap.Adapter.MedicineListAdapter
import com.pearl.medicap.R
import com.pearl.medicap.databinding.ActivityPrescriptionBinding
import com.pearl.medicap.model.MedicineListDAta
import java.util.*

class PrescriptionActivity : AppCompatActivity() {
    private val REQUEST_CODE_SPEECH_INPUT = 1
    var list=ArrayList<MedicineListDAta>()
    var value=0
    var amount=0
    var discount=0
    var sum=0
    lateinit var medicineListAdapter: MedicineListAdapter

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
                 binding.medicineSubtotalET.setText(value.toString())
             }
                binding.medicineQuantityET.setHint("Quality")

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

           if (s!=null){
               value=s.toString().toInt()
           }


            }
        })

        binding.medicineDiscountET.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!=null){

                    discount=s.toString().toInt()

                }

            }

            override fun afterTextChanged(s: Editable?) {
                binding.medicineSubtotalET.setText(discount.toString())

            }

        })
        binding.medicinePriceET.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                 amount=s.toString().toInt()
            }

            override fun afterTextChanged(s: Editable?) {
                 sum=value+discount+amount
                binding.medicineSubtotalET.setText(sum.toString())
            }

        })


        binding.addBtn.setOnClickListener {


           /* val result = buildString{
              //  append(medicine_name,",",dosage,",",frequency,",",",",duration,notes)
                append(binding.medicineNameET.text.toString()+""+sum.toString()+",\n"+binding.notes.text.toString())
            }
*/
            var medicine_dataList=findViewById<RecyclerView>(R.id.medicinedatalist)
            medicine_dataList.layoutManager=LinearLayoutManager(this)
            list.addAll(listOf(MedicineListDAta(binding.medicineNameET.text.toString(),binding.notes.text.toString(),sum.toString())))
            medicineListAdapter= MedicineListAdapter(this, list)
           // var adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
            medicine_dataList.adapter=medicineListAdapter
            binding.medicineNameET.text.clear()
            binding.notes.text.clear()
            binding.medicineQuantityET.setHint("Quality")

            var subtotal=0
            subtotal=subtotal+sum
            Log.d("total","subtotal==="+subtotal)
            binding.subtotalET.setText(subtotal.toString())

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