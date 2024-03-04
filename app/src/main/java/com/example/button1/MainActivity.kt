package com.example.button1

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultField: TextView // текстовое поле для вывода результата
    private lateinit var numberField: EditText   // поле для ввода числа
    private lateinit var operationField: TextView    // текстовое поле для вывода знака операции
    private var operand: Double? = null  // операнд операции
    private var lastOperation = "=" // последняя операция

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // получаем все поля по id из activity_main.xml
        resultField = findViewById<TextView>(R.id.resultField)
        numberField = findViewById<EditText>(R.id.numberField)
        operationField = findViewById<TextView>(R.id.operationField)

        findViewById<View>(R.id.add).setOnClickListener { view: View? ->
            onOperationClick("+")
        }
        findViewById<View>(R.id.sub).setOnClickListener { view: View? ->
            onOperationClick("-")
        }
        findViewById<View>(R.id.mul).setOnClickListener { view: View? ->
            onOperationClick("*")
        }
        findViewById<View>(R.id.div).setOnClickListener { view: View? ->
            onOperationClick("/")
        }
        findViewById<View>(R.id.eq).setOnClickListener { view: View? ->
            onOperationClick("=")
        }
        findViewById<View>(R.id.n0).setOnClickListener { view: View? ->
            onNumberClick("0")
        }
        findViewById<View>(R.id.n1).setOnClickListener { view: View? ->
            onNumberClick("1")
        }
        findViewById<View>(R.id.n2).setOnClickListener { view: View? ->
            onNumberClick("2")
        }
        findViewById<View>(R.id.n3).setOnClickListener { view: View? ->
            onNumberClick("3")
        }
        findViewById<View>(R.id.n4).setOnClickListener { view: View? ->
            onNumberClick("4")
        }
        findViewById<View>(R.id.n5).setOnClickListener { view: View? ->
            onNumberClick("5")
        }
        findViewById<View>(R.id.n6).setOnClickListener { view: View? ->
            onNumberClick("6")
        }
        findViewById<View>(R.id.n7).setOnClickListener { view: View? ->
            onNumberClick("7")
        }
        findViewById<View>(R.id.n8).setOnClickListener { view: View? ->
            onNumberClick("8")
        }
        findViewById<View>(R.id.n9).setOnClickListener { view: View? ->
            onNumberClick("9")
        }
        findViewById<View>(R.id.comma).setOnClickListener { view: View? ->
            onNumberClick(",")
        }
    }

    // сохранение состояния
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("OPERATION", lastOperation)
        if (operand != null) outState.putDouble("OPERAND", operand!!)
        super.onSaveInstanceState(outState)
    }

    // получение ранее сохраненного состояния
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastOperation = savedInstanceState.getString("OPERATION").toString()
        operand = savedInstanceState.getDouble("OPERAND")
        resultField.text = operand.toString()
        operationField.text = lastOperation
    }

    // обработка нажатия на числовую кнопку
    fun onNumberClick(number: String) {
        numberField.append(number)
        if (lastOperation == "=" && operand != null) {
            operand = null
        }
    }

    // обработка нажатия на кнопку операции
    fun onOperationClick(op: String) {
        var number = numberField.text.toString()
        // если введено что-нибудь
        if (number.isNotEmpty()) {
            number = number.replace(',', '.')
            try {
                performOperation(number.toDouble(), op)
            } catch (ex: NumberFormatException) {
                numberField.setText("")
            }
        }
        lastOperation = op
        operationField.text = lastOperation
    }

    private fun performOperation(number: Double, operation: String) {

        // если операнд ранее не был установлен (при вводе самой первой операции)
        if (operand == null) {
            operand = number
        } else {
            if (lastOperation == "=") {
                lastOperation = operation
            }
            when (lastOperation) {
                "=" -> operand = number
                "/" -> operand = if (number == 0.0) {
                    0.0
                } else {
                    operand!! / number
                }

                "*" -> operand = operand!! * number
                "+" -> operand = operand!! + number
                "-" -> operand = operand!! - number
            }
        }
        resultField.text = operand.toString().replace('.', ',')
        numberField.setText("")
    }
}
