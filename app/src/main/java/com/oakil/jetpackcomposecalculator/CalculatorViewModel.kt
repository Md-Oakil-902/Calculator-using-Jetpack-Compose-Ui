package com.oakil.jetpackcomposecalculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel(){
    val _equationText = MutableLiveData("")
    val equationText: LiveData<String>  = _equationText


    val _resultText = MutableLiveData("")
    val resultText: LiveData<String> = _resultText

    fun onButtonClick(btn: String){

        Log.i("Clicked Btn", btn)
        _equationText.value?.let{

            if(btn== "AC"){
                _equationText.value = ""
                _resultText.value = ""
                return
            }

            if (btn == "C") {
                if (it.isNotEmpty()) {
                    // If the last character is an operator, allow it to remain for further editing
                    if (it.last().isOperator()) {
                        return  // Don't remove operator if it's at the end
                    }

                    // Remove the last character and update the equation
                    _equationText.value = it.substring(0, it.length - 1)

                    // Recalculate the result if necessary (only if the equation is complete)
                    if (isValidEquation(_equationText.value.toString())) {
                        _resultText.value = calculateResult(_equationText.value.toString())
                    }else{
                        _resultText.value = ""
                    }
                }
                return
            }

            if(btn == "="){
 //               _equationText.value = _resultText.value

                return
            }
            else{
                _equationText.value = it + btn
            }


            try{
                _resultText.value = calculateResult(_equationText.value.toString())

            }catch (exception: Exception){

            }

        }


    }

    private fun isValidEquation(equation: String): Boolean {
        return equation.isNotEmpty() && !equation.last().isOperator()

    }


    fun calculateResult(equation:  String):String{
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable : Scriptable = context.initStandardObjects()
        var finalResult = context.evaluateString(scriptable, equation, "JavaScript", 1, null).toString()
        if(finalResult.endsWith(".0")){
            finalResult = finalResult.replace(".0", "")
        }
        return finalResult

    }

}

private fun Char.isOperator(): Boolean {
    return this== '+' || this == '-' || this == '*' || this == '/'

}
