package com.example.calculadora

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val SUMA = "+"
    val RESTA = "-"
    val MULTIPLICACION = "*"
    val DIVISION = "/"
    val PORCENTAJE = "%"

    var operacionActual = ""
    var primerNumero: Double = Double.NaN
    var segundoNumero: Double = Double.NaN

    lateinit var tvTemp: TextView
    lateinit var tvResult: TextView

    lateinit var formatoDecimal: DecimalFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        formatoDecimal = DecimalFormat("#.##########")
        tvTemp = findViewById(R.id.tvTemp)
        tvResult = findViewById(R.id.tvResult)
    }

    fun cambiarOperador(b: View) {
        val boton: Button = b as Button
        operacionActual = when (boton.text.toString().trim()) {
            "÷" -> "/"
            "x" -> "*"
            else -> boton.text.toString().trim()
        }
    }

    fun calcular(b: View) {
        if (!primerNumero.isNaN()) {
            segundoNumero = tvTemp.text.toString().toDoubleOrNull() ?: 0.0
            tvTemp.text = ""

            primerNumero = when (operacionActual) {
                "+" -> primerNumero + segundoNumero
                "-" -> primerNumero - segundoNumero
                "*" -> primerNumero * segundoNumero
                "/" -> if (segundoNumero != 0.0) primerNumero / segundoNumero else Double.NaN
                "%" -> primerNumero % segundoNumero
                else -> primerNumero
            }

            tvResult.text = formatoDecimal.format(primerNumero)
        } else {
            primerNumero = tvTemp.text.toString().toDoubleOrNull() ?: 0.0
        }
    }

    fun seleccionarNumero(b: View) {
        val boton: Button = b as Button
        if (tvTemp.text.toString() == "0") {
            tvTemp.text = ""
        }
        tvTemp.append(boton.text.toString())
    }
}
