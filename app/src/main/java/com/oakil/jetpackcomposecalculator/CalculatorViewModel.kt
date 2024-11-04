package com.oakil.jetpackcomposecalculator

import android.util.Log
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel(){

    fun onButtonClick(btn: String){
        Log.i("Clicked Btn")
    }
}