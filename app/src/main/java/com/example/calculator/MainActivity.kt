package com.example.arcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View){
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View){
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if (lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View){
        if (lastNumeric){
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")){  // The 'startsWith' checks if the string starts with a '-' in this case.
                    prefix = "-"
                    tvValue = tvValue.substring(1)  // If we get a result like -215, then the tvValue will
                }                                            // drop the '-' and store 215 as string, since the
                                                             // 'startIndex' is 1
                if (tvValue.contains("-")){
                    val splitValue = tvValue.split("-")    // TEXT SPLITTING USING THE 'split' function
                                                                     // The string will split at '-'
                    var one = splitValue[0]   // If the string is 99-1, then [0] = 99
                    var two = splitValue[1]   // [1] = 1

                    if (!prefix.isEmpty()){   // Since we ignore the '-' in the subString so we need to take that
                        one = prefix + one    // into consideration by checking if it's not empty.
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()){
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()){
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }else if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()){
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }



            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if (result.contains(".0")){
            value = result.substring(0, result.length - 2)  // If the result is 99.0, then it will store starting
        }
        return value                                        //         index -> 0123  from 0 and end at 2 (excluding)
    }                                                       // The final result will be 99 i.e. everything except '.0'

    fun onOperator(view: View){
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("+") || value.contains("-") || value.contains("*")
        }
    }
}