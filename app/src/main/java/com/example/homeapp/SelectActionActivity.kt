package com.example.homeapp

// Импортируем необходимые классы для работы
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.ComponentActivity

// Определяем класс SelectActionActivity, который является новой активностью в приложении
class SelectActionActivity : ComponentActivity() {

    // Объявляем константу для передачи данных между активностями
    companion object {
        const val BUTTON_CLICKED_KEY = "button_clicked"  // Ключ для сохранения, какой кнопкой был выполнен выбор
    }

    // Объявляем переменные для кнопок интерфейса
    private lateinit var createRequestButton: Button   // Кнопка для создания запроса
    private lateinit var createAppointmentButton: Button  // Кнопка для создания встречи
    private lateinit var backButton: ImageButton  // Кнопка для возврата назад

    // Метод, который вызывается при создании активности (когда она запускается)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливаем макет активности из XML-файла (activity_request)
        setContentView(R.layout.activity_request)

        // Инициализируем все виджеты (например, кнопки)
        initViews()
        // Настраиваем кнопку для создания запроса
        setupCreateRequestButton()
        // Настраиваем кнопку для создания встречи
        setupCreateAppointmentButton()
        // Настраиваем кнопку для возврата назад
        setupBackButton()
    }

    // Метод для инициализации кнопок и других элементов пользовательского интерфейса
    private fun initViews() {
        // Находим кнопку для создания запроса по её ID
        createRequestButton = findViewById(R.id.create_request_activity_button)
        // Находим кнопку для создания встречи по её ID
        createAppointmentButton = findViewById(R.id.create_appointment_activity_button)
        // Находим кнопку для возврата по её ID
        backButton = findViewById(R.id.back_btn)
    }

    // Метод для настройки действия при нажатии на кнопку создания запроса
    private fun setupCreateRequestButton() {
        createRequestButton.setOnClickListener {
            // Передадим в метод такую строку, чтобы приложение в будущем знало, что мы выбрали запрос
            startSelectionActivity("request_button")
        }
    }

    // Метод для настройки действия при нажатии на кнопку создания встречи
    private fun setupCreateAppointmentButton() {
        createAppointmentButton.setOnClickListener {
            // Передадим в метод такую строку, чтобы приложение в будущем знало, что мы выбрали встречу
            startSelectionActivity("appointment_button")
        }
    }

    // Метод для настройки действия при нажатии на кнопку возврата
    private fun setupBackButton() {
        backButton.setOnClickListener {
            // Завершаем текущую активность и возвращаемся на предыдущий экран
            finish()
        }
    }

    // Метод для запуска новой активности SelectionActivity и передачи в неё информации о нажатой кнопке
    private fun startSelectionActivity(buttonClicked: String) {
        // Создаем намерение для запуска активности SelectionActivity
        val intent = Intent(this, SelectionActivity::class.java).apply {
            // Передаем в активность данные о том, какая кнопка была нажата
            putExtra(BUTTON_CLICKED_KEY, buttonClicked) // buttonClicked и есть наша переданная строка
        }
        // Запускаем новую активность
        startActivity(intent)
    }
}
