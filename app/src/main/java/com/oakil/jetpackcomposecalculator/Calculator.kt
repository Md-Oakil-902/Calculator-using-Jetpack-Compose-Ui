package com.oakil.jetpackcomposecalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val buttonList = listOf(
    "C", "(", ")", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "-",
    "1", "2", "3", "+",
    "AC", "0", ".", "="
)



@Composable
fun Calculator(modifier: Modifier = Modifier, viewModel: CalculatorViewModel) {
    val equationText = viewModel.equationText.observeAsState().value ?: ""
    val resultText = viewModel.resultText.observeAsState().value ?: ""

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            StyledEquationText(equationText = equationText)
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = resultText,
                style = TextStyle(fontSize = 60.sp, textAlign = TextAlign.End),
                maxLines = 2,
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4)
            ) {
                items(buttonList) {
                    CalculatorButton(btn = it, onclick = {
                        viewModel.onButtonClick(it)
                    })
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(btn: String, onclick: ()->Unit) {
    Box(modifier = Modifier.padding(8.dp)){
        FloatingActionButton(
            onClick = onclick,
            modifier = Modifier.size(80.dp),
            contentColor = Color.White, // number will be red
            containerColor = getColor(btn) // button container color
        ) {
            Text(text= btn, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }

    }

}


fun getColor(btn : String): Color{
    if(btn =="C" || btn == "AC")
        return Color(0xFFF44336)
    if(btn == "(" || btn == ")")
        return Color.Gray
    if(btn=="/" || btn == "*" || btn == "+" || btn == "-" || btn == "=")
        return Color(0xFFFFC107)

    return Color(0xff00C8C9)

}

@Composable
fun StyledEquationText(equationText: String) {
    // Split the equation into its parts (numbers and operators)
    val operatorColor = Color(0xFFFFC107) // Amber color for operators
    val numberColor = Color.White // White color for numbers (or you can choose other colors)

    // Create a styled string
    val annotatedString = buildAnnotatedString {
        var isOperator = false

        // Iterate through each character in the equation string
        equationText.forEach { char ->
            if (char in listOf('+', '-', '*', '/', '=')) {
                // For operators, apply the operator color
                withStyle(style = SpanStyle(color = operatorColor)) {
                    append(char.toString())
                }
                isOperator = true
            } else {
                // For numbers, apply the number color
                withStyle(style = SpanStyle(color = numberColor)) {
                    append(char.toString())
                }
            }
        }
    }

    // Display the styled equation
    Text(
        text = annotatedString,
        style = TextStyle(fontSize = 30.sp, textAlign = TextAlign.End),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(20.dp)
    )
}


@Composable
fun PreviewCalculator() {
    // Mock ViewModel
    val viewModel = CalculatorViewModel()

    // Set some initial values for the equation and result (optional)
    viewModel._equationText.value = "22 +"
    viewModel._resultText.value = "22"

    // Display the Calculator composable with the mock ViewModel
    Calculator(viewModel = viewModel)
}
@Preview(showBackground = true)
@Composable
fun PreviewCalculatorUI() {
    PreviewCalculator() // Use the preview composable here
}















