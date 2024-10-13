package com.example.homeapp

// Импортируем нужные классы для работы
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

// Определяем основной класс MainActivity, который является активностью приложения
class MainActivity : ComponentActivity() {

    // Объявляем переменную для кнопки, которая будет использоваться для создания запроса
    private lateinit var createRequestButton: Button

    // Метод, который вызывается при создании активности (когда она запускается)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливаем макет активности из XML-файла (activity_main)
        setContentView(R.layout.activity_main)

        // Инициализируем виджеты (например, кнопки)
        initViews()
        // Настраиваем кнопку для запуска новой активности
        setupStartButton()
    }

    // Метод для инициализации кнопок и других элементов пользовательского интерфейса
    private fun initViews() {
        // Находим кнопку по её идентификатору (R.id.create_request_button)
        createRequestButton = findViewById(R.id.create_request_button)
    }

    // Метод для установки действия при нажатии на кнопку
    private fun setupStartButton() {
        // Устанавливаем слушатель нажатия на кнопку
        createRequestButton.setOnClickListener {
            // При нажатии на кнопку запускаем новую активность SelectActionActivity
            startActivity(Intent(this, SelectActionActivity::class.java))
        }
    }
}
