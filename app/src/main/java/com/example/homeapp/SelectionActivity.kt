package com.example.homeapp

// Импортируем необходимые классы для работы
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity

// Определяем класс SelectionActivity, который предоставляет пользователю выбор типа действия
class SelectionActivity : ComponentActivity() {

    // Объявляем константу для передачи данных между активностями
    companion object {
        const val BUTTON_CLICKED_KEY = "button_clicked"  // Ключ для сохранения, какая кнопка была нажата
    }

    // Переменная для хранения значения, какая кнопка была нажата в предыдущей активности
    private var buttonClicked: String? = null

    // Объявляем переменные для элементов интерфейса
    private lateinit var requestTypes: Array<String>  // Массив для типов запросов или встреч
    private lateinit var spinnerRequestType: Spinner  // Выпадающий список для выбора типа
    private lateinit var textViewType: TextView  // Текстовое поле для показа описания выбора
    private lateinit var editTextSpecialist: EditText  // Поле ввода для специалиста (для встреч)
    private lateinit var textViewSpecialist: TextView  // Текстовое описание поля для специалиста
    private lateinit var buttonSubmit: Button  // Кнопка для подтверждения выбора
    private lateinit var backButton: ImageButton  // Кнопка для возврата назад

    // Метод, который вызывается при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливаем макет активности из XML-файла (activity_selection)
        setContentView(R.layout.activity_selection)

        // Инициализируем элементы пользовательского интерфейса
        initViews()
        // Получаем данные о том, какая кнопка была нажата в предыдущей активности
        retrieveExtras()
        // Настраиваем выпадающий список (Spinner) в зависимости от выбора пользователя
        setupSpinner()
        // Настраиваем отображение текстовых полей в зависимости от выбора
        setupTextView()
        // Настраиваем действие для кнопки подтверждения
        setupButtonSubmit()
        // Настраиваем действие для кнопки возврата
        setupBackButton()
    }

    // Метод для инициализации всех элементов интерфейса
    private fun initViews() {
        spinnerRequestType = findViewById(R.id.spinner_request_type)  // Находим выпадающий список по ID
        textViewType = findViewById(R.id.textView2)  // Находим текстовое поле по ID
        editTextSpecialist = findViewById(R.id.editText_specialist)  // Находим поле ввода для специалиста
        textViewSpecialist = findViewById(R.id.textView6)  // Находим текстовое описание для специалиста
        buttonSubmit = findViewById(R.id.button_submit)  // Находим кнопку подтверждения
        backButton = findViewById(R.id.back_btn)  // Находим кнопку возврата
    }

    // Метод для получения данных, переданных из предыдущей активности (какая кнопка была нажата)
    private fun retrieveExtras() {
        // Получаем значение, переданное с предыдущей активности, с помощью ключа BUTTON_CLICKED_KEY
        buttonClicked = intent.getStringExtra(BUTTON_CLICKED_KEY)
    }

    // Метод для настройки выпадающего списка в зависимости от того, что выбрал пользователь
    private fun setupSpinner() {
        // Определяем, какие типы запросов показывать в зависимости от нажатой кнопки
        requestTypes = when (buttonClicked) {
            // Если была нажата кнопка создания запроса, загружаем массив типов запросов
            "request_button" -> resources.getStringArray(R.array.request_types)
            // Если была нажата кнопка создания встречи, загружаем массив типов встреч
            "appointment_button" -> resources.getStringArray(R.array.appointment_types)
            else -> emptyArray()  // Если что-то пошло не так, массив остаётся пустым
        }

        // Создаём адаптер для выпадающего списка и передаём в него данные
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, requestTypes)
        spinnerRequestType.adapter = adapter  // Применяем адаптер к нашему Spinner
    }

    // Метод для настройки текстовых полей в зависимости от нажатой кнопки
    private fun setupTextView() {
        // В зависимости от того, что выбрал пользователь, изменяем текст на экране
        textViewType.text = when (buttonClicked) {
            // Если была нажата кнопка создания запроса, показываем текст для запроса
            "request_button" -> getString(R.string.select_request_str)
            // Если была нажата кнопка создания встречи, показываем текст для встречи
            "appointment_button" -> {
                // Для встречи делаем видимыми поля для специалиста
                editTextSpecialist.visibility = View.VISIBLE
                textViewSpecialist.visibility = View.VISIBLE
                getString(R.string.select_appointment_str)
            }
            // Если кнопка неизвестна, показываем текст ошибки
            else -> getString(R.string.application_malfunction_str)
        }
    }

    // Метод для настройки кнопки подтверждения
    private fun setupButtonSubmit() {
        buttonSubmit.setOnClickListener {
            // Проверяем, требуется ли ввод имени специалиста
            if (buttonClicked == "appointment_button" && editTextSpecialist.text.isEmpty()) {
                // Если имя специалиста не введено, показываем сообщение об ошибке
                editTextSpecialist.error = getString(R.string.required_field_str)
                return@setOnClickListener
            }

            // Создаём намерение для следующей активности
            val intent = getNextIntent()
            // Передаём в новую активность информацию о нажатой кнопке
            intent.putExtra(BUTTON_CLICKED_KEY, buttonClicked)
            // Запускаем новую активность
            startActivity(intent)
        }
    }


    // Метод для настройки кнопки возврата
    private fun setupBackButton() {
        backButton.setOnClickListener {
            // Завершаем текущую активность и возвращаемся на предыдущий экран
            finish()
        }
    }

    // Метод для создания намерения для следующей активности в зависимости от нажатой кнопки
    private fun getNextIntent(): Intent {
        // В зависимости от нажатой кнопки выбираем следующую активность
        return when (buttonClicked) {
            // Если нажата кнопка создания запроса или встречи, переходим на CommentActivity
            "request_button", "appointment_button" -> Intent(this, CommentActivity::class.java)
            // В случае ошибки переходим на активность для обработки неполадок
            else -> Intent(this, ApplicationMalfunctionActivity::class.java)
        }
    }
}
