package com.pearl.medicap.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pearl.medicap.Adapter.MedicineListAdapter
import com.pearl.medicap.R
import com.pearl.medicap.databinding.ActivityPrescriptionBinding
import com.pearl.medicap.model.MedicineListDAta
import com.pearl.medicap.pearlLib.BaseClass
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class PrescriptionActivity : BaseClass() {
    private val REQUEST_CODE_SPEECH_INPUT = 1
    var list = ArrayList<MedicineListDAta>()
    var quantity = 0
    var cost = 0.0
    var discount = 0.0
    var amount = 0.0
    var sum = 0.0
    lateinit var medicineListAdapter: MedicineListAdapter

    lateinit var binding: ActivityPrescriptionBinding
    override fun setLayoutXml() {
        TODO("Not yet implemented")
    }

    override fun initializeViews() {
        TODO("Not yet implemented")
    }

    override fun initializeClickListners() {
        var backBtn = findViewById<ImageView>(R.id.iv_back)
        backBtn.setOnClickListener {
            this.finish()
        }
    }

    override fun initializeInputs() {
        TODO("Not yet implemented")
    }

    override fun initializeLabels() {
        TODO("Not yet implemented")
    }

    override fun changeStatusBarColor() {
        TODO("Not yet implemented")
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_prescription)
        window.statusBarColor = resources.getColor(R.color.App_color)
        initializeClickListners()
        binding.mic.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                // on below line we are displaying error message in toast
                Toast.makeText(this, " " + e.message, Toast.LENGTH_SHORT).show()
            }
        }


//        binding.medicineQuantityET.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
////                var num = 0
//                if (!s?.toString().equals("null")) {
//
////                    binding.medicineSubtotalET.setText(sum)
//                    binding.medicineQuantityET.setHint("Quality")
//                }
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                if (s != null && s != "") {
//                    quantity = Integer.parseInt(s.toString())
//                    if(s==""){
//                        quantity = 0
//                    }
//                }
//                else{
//                    quantity = 0
//                }
//
//            }
//        })

//        binding.medicineDiscountET.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                var d = s.toString()
//
//                if (d != null && d != "") {
//                    discount = Integer.parseInt(d).toDouble()
//                    if(d==""){
//                        discount = 0.0
//                    }
//                }
//                else{
//                    discount = 0.0
//                }
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
////                binding.medicineSubtotalET.setText(sum)
//
//            }
//
//        })

//        binding.medicinePerPriceET.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                cost = s.toString().toDouble()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
////                binding.medicineSubtotalET.setText(sum)
//            }
//        })

        binding.addBtn.setOnClickListener {

            if (binding.medicineQuantityET != null && binding.medicineDiscountET != null && binding.medicinePerPriceET != null) {
                quantity = Integer.parseInt(binding.medicineQuantityET.text.toString())
                discount = Integer.parseInt(binding.medicineDiscountET.text.toString()).toDouble()
                cost = Integer.parseInt(binding.medicinePerPriceET.text.toString()).toDouble()

                if (quantity != null || quantity != 0 && discount != null || discount != 0.0 && cost != null || cost != 0.0) {
                    Log.d(
                        "Medicine total",
                        "quantity:::Discount:::cost" + quantity + "," + discount + "," + cost
                    )
                    discount = (binding.medicineDiscountET.text.toString().toDouble() / 100)
                    amount = (quantity * cost)
                    sum = (amount - (amount * discount))
//                    binding.medicineSubtotalET.setText(sum.toString())




//            Log.d("Medicine total", "quantity:::Discount:::cost:::amount:::sum" + quantity + "," + discount+","+cost+","+amount+","+sum)

                    /* val result = buildString{
                       //  append(medicine_name,",",dosage,",",frequency,",",",",duration,notes)
                         append(binding.medicineNameET.text.toString()+""+sum.toString()+",\n"+binding.notes.text.toString())
                     }
         */
                    var medicine_dataList = findViewById<RecyclerView>(R.id.medicinedatalist)
                    medicine_dataList.layoutManager = LinearLayoutManager(this)

                    list.addAll(
                        listOf(
                            MedicineListDAta(
                                binding.medicineNameET.text.toString(),
                                binding.medicineQuantityET.text.toString(),
                                binding.medicineDiscountET.text.toString(),
                                binding.medicinePerPriceET.text.toString(),
                                binding.notes.text.toString(),
                                sum.toString()
                            )
                        )
                    )



                    if ((binding.subtotalET.text).toString() != null && (binding.subtotalET.text).toString() != "") {
                        var subtotal = (binding.subtotalET.text).toString()
                        sum += subtotal.toDouble()
                        Log.d("medicine", "Sub total:::" + sum)

                    } else {
                        sum = sum
                    }
                    binding.subtotalET.setText(roundOffDecimal(sum).toString())

                    var gst = 0.12
                    binding.gstET.setText(gst.toString())
                    var total = 0.0
                    var subtotal = (binding.subtotalET.text).toString()

                    if ((binding.subtotalET.text).toString() != null && (binding.subtotalET.text).toString() != "") {
                        total = subtotal.toDouble() + (subtotal.toDouble() * gst)
                    } else {
                        total = sum + (sum * gst)
                    }


                    binding.totalET.setText(roundOffDecimal(total).toString())

                    medicineListAdapter = MedicineListAdapter(this, list)
                    // var adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
                    medicine_dataList.adapter = medicineListAdapter
                    binding.medicineNameET.text.clear()
                    binding.medicineDiscountET.text.clear()
                    binding.notes.text.clear()
                    sum = 0.0
                    binding.medicineQuantityET.text.clear()
                    binding.medicinePerPriceET.text.clear()

//            var subtotal = 0
//            subtotal = subtotal + sum.toInt()
//            Log.d("total", "subtotal===" + subtotal)
//            binding.subtotalET.setText(subtotal.toString())

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
                } else {
                    Toast.makeText(this, "Please Fill all details", Toast.LENGTH_SHORT).show()
                }
            }


//
        }


    }

    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
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
    }

}