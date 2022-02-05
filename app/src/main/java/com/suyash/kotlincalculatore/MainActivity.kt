package com.suyash.kotlincalculatore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    private var isLastNumber: Boolean = false
    private var isLastDecimal: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        isLastNumber = true
        isLastDecimal = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimal(view: View) {
        if (isLastNumber && !isLastDecimal) {
            tvInput?.append(".")
            isLastNumber = false
            isLastDecimal = true
        }
    }

    fun onEqual(view: View) {
        if (isLastNumber) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {

                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue.substring(1)
                }

                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDecimal((one.toDouble() - two.toDouble()).toString())
                }
                else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDecimal((one.toDouble() + two.toDouble()).toString())

                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDecimal((one.toDouble() * two.toDouble()).toString())


                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDecimal((one.toDouble() / two.toDouble()).toString())
                }


            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDecimal (result:String) : String{
        var value = result
        if(result.contains(".0")){          "90.0"
            value = result.substring(0,result.length - 2)
        }
        return value
    }


    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (isLastNumber && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                isLastNumber = false
                isLastDecimal = false
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("+") || value.contains("/") || value.contains("*") || value.contains("-")
        }
    }
}