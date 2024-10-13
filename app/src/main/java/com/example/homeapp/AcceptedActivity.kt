package com.example.homeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class AcceptedActivity : ComponentActivity() {

    companion object {
        const val BUTTON_CLICKED_KEY = "button_clicked"  // Константа для передачи типа нажатой кнопки
    }

    private var buttonClicked: String? = null  // Переменная для хранения информации о нажатой кнопке
    private lateinit var statusTextView: TextView  // Текстовое поле для отображения статуса
    private lateinit var menuButton: Button  // Кнопка для перехода в меню

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accepted)  // Установка макета для этой активности

        initViews()  // Инициализация элементов интерфейса
        retrieveExtras()  // Получение данных, переданных из предыдущей активности
        setupViews()  // Настройка элементов интерфейса в зависимости от переданных данных
    }

    // Инициализация UI-элементов
    private fun initViews() {
        statusTextView = findViewById(R.id.textView2)  // Текстовое поле для статуса
        menuButton = findViewById(R.id.menu_btn)  // Кнопка для возврата в меню
    }

    // Получение переданных данных
    private fun retrieveExtras() {
        buttonClicked = intent.getStringExtra(BUTTON_CLICKED_KEY)  // Получаем значение, переданное из предыдущей активности
    }

    // Настройка интерфейса
    private fun setupViews() {
        // Установка текста в зависимости от того, какая кнопка была нажата
        statusTextView.text = when (buttonClicked) {
            "appointment_button" -> getString(R.string.appointment_accepted_str)  // Если была выбрана запись на встречу
            "request_button" -> getString(R.string.request_accepted_str)  // Если была выбрана заявка
            else -> getString(R.string.application_malfunction_str)  // Если произошла ошибка
        }

        // Обработчик нажатия кнопки "Меню", который возвращает пользователя в главное меню приложения
        menuButton.setOnClickListener {
            // Переход в MainActivity и очистка стека задач
            val intent = Intent(applicationContext, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)  // Очищаем предыдущие активности
            }
            startActivity(intent)  // Запуск MainActivity
        }
    }
}
