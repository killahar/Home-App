package com.example.homeapp

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.ComponentActivity

class ApplicationMalfunctionActivity : ComponentActivity() {

    // Определение кнопки "Назад"
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_malfunction)  // Установка макета для этой активности

        initViews()  // Инициализация UI-элементов
        setupBackButton()  // Настройка кнопки возврата
    }

    // Метод для инициализации элементов интерфейса
    private fun initViews() {
        backButton = findViewById(R.id.back_btn)  // Получаем ссылку на кнопку "Назад"
    }

    // Метод для настройки кнопки возврата
    private fun setupBackButton() {
        backButton.setOnClickListener {
            finish()  // Завершение активности, что приводит к возврату на предыдущий экран
        }
    }
}
