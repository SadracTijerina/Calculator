package sadrac.tijerina.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput : TextView? = null
    private var lastNumeric : Boolean = false
    private var lastDecimal : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDecimal = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDecimal) {
            tvInput?.append(".")
            lastNumeric = false
            lastDecimal = true
        }

    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(!isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDecimal = false
            }

        }
    }

    fun onEqual(view: View) {

        if(lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try {
                if(tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZero((one.toDouble() - two.toDouble()).toString())
                } else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZero((one.toDouble() + two.toDouble()).toString())
                } else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZero((one.toDouble() * two.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZero((one.toDouble() / two.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }

        }

    }

    private fun removeZero(result: String) : String {
        var value = result
        if (result.contains(".0")) {
          value = result.substring(0, result.length - 2)
        }

        return value
    }

    private fun isOperatorAdded(value : String) : Boolean{
        if(value.startsWith("-") && value.length == 1) {
            return false
        }else if (value.contains("/") || value.contains("*") || value.contains("+")){
            return true
        }else if (value.length > 1){
            var temp = value.subSequence(1, value.length-1)
            return temp.contains("-")
        }else{
            return false
        }
    }

}