package com.tip.tipcal

import android.animation.ArgbEvaluator
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.time.temporal.TemporalAmount

private const val TAG="MainActivit"
private const val initialTipPrecent=15

class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercent: TextView
    private lateinit var tvTipAmount:TextView
    private lateinit var tvTotalAmount:TextView
    private lateinit var tvTipdec:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.pAmount)
        seekBarTip = findViewById(R.id.seekBar)
        tvTipPercent = findViewById(R.id.TipPercentLabel)
        tvTipAmount = findViewById(R.id.tvTip)
        tvTotalAmount = findViewById(R.id.totalAmount)
        tvTipdec=findViewById(R.id.tipDec)

        seekBarTip.progress=initialTipPrecent
        updateDescription(initialTipPrecent)
        tvTipPercent.text="$initialTipPrecent%"
        tvTotalAmount.setTextColor(Color.parseColor("#FFFFFF"));
        tvTipAmount.setTextColor(Color.parseColor("#FFFFFF"));
        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG,  "onProgressChanged $p1")
                tvTipPercent.text ="$p1%"
                computeTipANdTotal()
                updateDescription(p1)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}



            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        etBaseAmount.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG,"afterTextChanged $s")
                computeTipANdTotal()
            }


            })
    }

    private fun updateDescription(tipP:Int ) {
        val tipDes=when(tipP){
            in  0 ..9 -> "Poor service \uD83E\uDD79"
            in 10..14 -> "Acceptable service \uD83E\uDEE1"
            in 15..19 ->"Good service \uD83D\uDE0A"
            in 20..24 ->"Great service \uD83E\uDD29"
            else -> "Amazing service  \uD83E\uDD70"
        }
        tvTipdec.text=tipDes

        //update color based on tip
        val color= ArgbEvaluator().evaluate(
            (tipP).toFloat()/seekBarTip.max,
            ContextCompat.getColor(this,R.color.red),
            ContextCompat.getColor(this,R.color.green)

        ) as Int
    tvTipdec.setTextColor(color)
    }

    private fun computeTipANdTotal() {
        if(etBaseAmount.text.isEmpty()){
            tvTipAmount.text=""
            tvTotalAmount.text=""
            return
        }
       val baseAmount= etBaseAmount.text.toString().toDouble()
        val tipPercent=seekBarTip.progress

         val tipAmount= baseAmount *tipPercent/100
        val totalAmount=baseAmount+tipAmount

        tvTipAmount.text="%.2f".format(tipAmount)
        tvTotalAmount.text="%.2f".format(totalAmount)

    }
    }
