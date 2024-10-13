package com.example.homeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.ComponentActivity

// Определение активности завершения авторизации
class EndAuthorizationActivity : ComponentActivity() {

    // Константа для передачи информации о нажатой кнопке
    companion object {
        const val BUTTON_CLICKED_KEY = "button_clicked"
    }

    // Объявляем переменные для кнопок и данных
    private lateinit var buttonSubmit: Button
    private lateinit var backButton: ImageButton
    private lateinit var surnameEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var addressEditText: EditText
    private var buttonClicked: String? = null

    // Метод, вызываемый при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_authorization)  // Устанавливаем макет активности

        initViews()  // Инициализируем элементы интерфейса
        retrieveExtras()  // Получаем переданные данные
        setupViews()  // Настраиваем действия кнопок
    }

    // Метод для инициализации кнопок и полей ввода
    private fun initViews() {
        backButton = findViewById(R.id.back_btn)  // Кнопка "Назад"
        buttonSubmit = findViewById(R.id.button_submit)  // Кнопка "Отправить"
        surnameEditText = findViewById(R.id.surname)  // Поле ввода фамилии
        nameEditText = findViewById(R.id.name)  // Поле ввода имени
        addressEditText = findViewById(R.id.apartment)  // Поле ввода адреса
    }

    // Метод для получения переданных данных (какая кнопка была нажата в предыдущей активности)
    private fun retrieveExtras() {
        buttonClicked = intent.getStringExtra(BUTTON_CLICKED_KEY)  // Получаем данные о нажатой кнопке
    }

    // Метод для настройки действий кнопок
    private fun setupViews() {
        // Обработчик нажатия кнопки "Отправить"
        buttonSubmit.setOnClickListener {
            // Проверяем, заполнены ли обязательные поля
            if (isInputValid()) {
                navigateToNextScreen(buttonClicked)  // Переход на следующий экран
            }
        }

        // Обработчик нажатия кнопки "Назад"
        backButton.setOnClickListener {
            finish()  // Завершаем активность, возвращаясь на предыдущий экран
        }
    }

    // Метод для проверки заполнения обязательных полей
    private fun isInputValid(): Boolean {
        // Сбрасываем предыдущие ошибки
        surnameEditText.error = null
        nameEditText.error = null
        addressEditText.error = null

        val surname = surnameEditText.text.toString().trim()  // Получаем текст из поля фамилии
        val name = nameEditText.text.toString().trim()  // Получаем текст из поля имени
        val address = addressEditText.text.toString().trim()  // Получаем текст из поля адреса

        // Проверяем, заполнены ли все обязательные поля
        return when {
            surname.isEmpty() -> {
                surnameEditText.error = getString(R.string.required_field_str)  // Устанавливаем сообщение об ошибке
                false
            }
            name.isEmpty() -> {
                nameEditText.error = getString(R.string.required_field_str)  // Устанавливаем сообщение об ошибке
                false
            }
            address.isEmpty() -> {
                addressEditText.error = getString(R.string.required_field_str)  // Устанавливаем сообщение об ошибке
                false
            }
            else -> true  // Все поля заполнены
        }
    }

    // Метод для перехода на следующий экран
    private fun navigateToNextScreen(buttonClicked: String?) {
        // Создаем намерение для перехода на экран подтверждения
        val intent = Intent(this, AcceptedActivity::class.java)
        intent.putExtra(BUTTON_CLICKED_KEY, buttonClicked)  // Передаем информацию о нажатой кнопке
        startActivity(intent)  // Запускаем активность
    }
}
