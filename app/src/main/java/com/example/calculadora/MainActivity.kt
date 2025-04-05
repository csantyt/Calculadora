package com.example.calculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvTemp: TextView

    private var operador: String? = null
    private var numeroAnterior: String = ""
    private var nuevoNumero: String = ""
    private var resultadoMostrado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
        tvTemp = findViewById(R.id.tvTemp)
    }

    fun seleccionarNumero(view: View) {
        val button = view as Button
        val numero = button.text.toString()

        // Si se mostró un resultado o hay un solo 0, se reemplaza
        if (resultadoMostrado || tvResult.text.toString() == "0") {
            tvResult.text = numero
            resultadoMostrado = false
        } else {
            tvResult.append(numero)
        }
    }

    fun seleccionarDecimal(view: View) {
        if (resultadoMostrado) {
            tvResult.text = "0."
            resultadoMostrado = false
        } else if (!tvResult.text.contains(".")) {
            if (tvResult.text.isEmpty()) {
                tvResult.text = "0."
            } else {
                tvResult.append(".")
            }
        }
    }

    fun cambiarOperador(view: View) {
        val button = view as Button
        val simbolo = button.text.toString()

        // Convertimos símbolos visuales a operadores reales
        operador = when (simbolo) {
            "+" -> "+"
            "-" -> "-"
            "x" -> "*"
            "÷" -> "/"
            "%" -> "%"
            else -> {
                tvResult.text = "Op. Inv"
                return
            }
        }

        if (tvResult.text.isNotEmpty()) {
            numeroAnterior = tvResult.text.toString()
            tvTemp.text = "$numeroAnterior $simbolo"
            tvResult.text = ""
        }
    }


    fun igual(view: View) {
        if (operador == null || tvResult.text.isEmpty()) return

        nuevoNumero = tvResult.text.toString()
        val resultado = operar(numeroAnterior, nuevoNumero, operador!!)
        tvResult.text = resultado
        tvTemp.text = "$numeroAnterior $operador $nuevoNumero"
        resultadoMostrado = true
    }

    fun limpiar(view: View) {
        tvResult.text = ""
        tvTemp.text = ""
        operador = null
        numeroAnterior = ""
        nuevoNumero = ""
        resultadoMostrado = false
    }

    fun borrarUltimo(view: View) {
        val texto = tvResult.text.toString()
        if (texto.isNotEmpty()) {
            tvResult.text = texto.dropLast(1)
        }
    }

    private fun operar(num1: String, num2: String, op: String): String {
        val n1 = num1.toDoubleOrNull() ?: return "Error"
        val n2 = num2.toDoubleOrNull() ?: return "Error"
        val resultado = when (op) {
            "+" -> n1 + n2
            "-" -> n1 - n2
            "*" -> n1 * n2
            "/" -> if (n2 != 0.0) n1 / n2 else return "Div/0"
            else -> return "Op. Inv"
        }
        return if (resultado % 1 == 0.0) resultado.toInt().toString() else resultado.toString()
    }
}
