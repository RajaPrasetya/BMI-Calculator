package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bmicalculator.ui.theme.BMICalculatorTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(modifier: Modifier = Modifier) {
    val selectedGender = remember {
        mutableStateOf("Laki-Laki")
    }
    val weight = remember {
        mutableStateOf(0)
    }
    val height = remember {
        mutableStateOf(0)
    }
    val age = remember {
        mutableStateOf(0)
    }
    val showDialog = remember {
        mutableStateOf(false)
    }
    val dialogMessage = remember {
        mutableStateOf("")
    }


    fun calculateBMI() {
        val heightInMeter = (height.value * height.value) / 10000.0
        val bmi = weight.value / heightInMeter
        val status = when {
            bmi < 0 -> "Tidak valid"
            bmi <= 15.0 -> "Terlalu Sangat Kurus"
            bmi <= 16.0 -> "Sangat Kurus"
            bmi <= 18.5 -> "Kurus"
            bmi <= 25.0 -> "Normal"
            bmi <= 30.0 -> "Gemuk"
            bmi <= 35.0 -> "Cukup Gemuk"
            bmi <= 40.0 -> "Sangat Gemuk"
            else -> "Terlalu Sangat Gemuk"
        }
        // Show dialog with BMI and status
        dialogMessage.value = "Jenis Kelamin: ${selectedGender.value}\n\nBMI: ${String.format(Locale.US, "%.2f", bmi)}\nStatus: $status\n\n Umur: ${age.value} tahun"
        showDialog.value = true
    }

    Scaffold( modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "BMI Calculator")
                }
            )
        }
    ) {
        paddingValues -> Column(modifier =
    Modifier
        .fillMaxSize()
        .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Content
            //Row jenis kelamin
            Row(
                modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
            ) {
                OutlinedButton(onClick = { selectedGender.value = "Laki-Laki"},
                    modifier.fillMaxWidth(0.45f),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        if (selectedGender.value == "Laki-Laki") MaterialTheme.colorScheme.primary else Color.White
                    )
                ) {
                    Text(
                        text = "Laki-Laki",
                        color = if (selectedGender.value == "Laki-Laki") Color.White else MaterialTheme.colorScheme.primary
                    )
                }
                OutlinedButton(onClick = { selectedGender.value = "perempuan"},
                    modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        if (selectedGender.value == "perempuan") MaterialTheme.colorScheme.primary else Color.White
                    )
                ) {
                    Text(
                        text = "Perempuan",
                        color = if (selectedGender.value == "perempuan") Color.White else MaterialTheme.colorScheme.primary
                    )
                }
            }
            //Row berat badan
            Text(text = "Bobot (kg)",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { weight.value-- }) {
                    Text("-")
                }
                //textfield berat badan width 80dp
                TextField(
                    value = weight.value.toString(),
                    onValueChange = { weight.value = it.toIntOrNull() ?: 0 },
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                    singleLine = true,
                    modifier = Modifier.width(80.dp),
                    textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Button(onClick = { weight.value++ }) {
                    Text("+")
                }
            }
            //Row tinggi badan
            Text(text = "Tinggi (cm)",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                modifier = Modifier.padding(top = 20.dp)
            )
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { height.value-- }) {
                    Text("-")
                }
                //textfield tinggi badan width 80dp
                TextField(
                    value = height.value.toString(),
                    onValueChange = { height.value = it.toIntOrNull() ?: 0 },
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                    singleLine = true,
                    modifier = Modifier.width(80.dp),
                    textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Button(onClick = { height.value++ }) {
                    Text("+")
                }
            }
            //Row umur
            Text(text = "Umur",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                modifier = Modifier.padding(top = 20.dp)
            )
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { age.value-- }) {
                    Text("-")
                }
                //textfield umur width 80dp
                TextField(
                    value = age.value.toString(),
                    onValueChange = { age.value = it.toIntOrNull() ?: 0 },
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                    singleLine = true,
                    modifier = Modifier.width(80.dp),
                    textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Button(onClick = { age.value++ }) {
                    Text("+")
                }
            }
            //Row button hitung
            Button(onClick = { if (weight.value != 0 && height.value != 0 && age.value != 0) {
                calculateBMI()
            } },
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                enabled = !(weight.value == 0 && height.value == 0 && age.value == 0)
            ) {
                Text(text = "Hitung")
            }
            //Dialog
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text(text = "Hasil Perhitungan BMI") },
                    text = { Text(text = dialogMessage.value) },
                    confirmButton = {
                        Button(onClick = { showDialog.value = false }) {
                            Text(text = "Close")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainViewPreview() {
    MainView()
}