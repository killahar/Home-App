package com.example.homeapp

// Импортируем необходимые классы для работы
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.ComponentActivity

// Определяем класс CommentActivity, который позволяет пользователю оставить комментарий
class CommentActivity : ComponentActivity() {

    // Объявляем константу для передачи данных между активностями
    companion object {
        const val BUTTON_CLICKED_KEY = "button_clicked"  // Ключ для сохранения, какая кнопка была нажата в предыдущих активностях
    }

    // Переменная для хранения значения, какая кнопка была нажата в предыдущей активности
    private var buttonClicked: String? = null

    // Объявляем переменные для элементов интерфейса
    private lateinit var buttonSubmit: Button  // Кнопка для отправки комментария
    private lateinit var backButton: ImageButton  // Кнопка для возврата назад
    private lateinit var editTextComment: EditText // Поле ввода Комментария

    // Метод, который вызывается при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливаем макет активности из XML-файла (activity_comment)
        setContentView(R.layout.activity_comment)

        // Инициализируем элементы пользовательского интерфейса
        initViews()
        // Получаем данные о том, какая кнопка была нажата в предыдущей активности
        retrieveExtras()
        // Настраиваем кнопку отправки комментария
        setupButtonSubmit()
        // Настраиваем кнопку возврата
        setupBackButton()
    }

    // Метод для инициализации всех элементов интерфейса
    private fun initViews() {
        // Находим кнопку отправки по её ID
        buttonSubmit = findViewById(R.id.button_submit)
        // Находим кнопку возврата по её ID
        backButton = findViewById(R.id.back_btn)
        // Находим поле ввода по его ID
        editTextComment = findViewById(R.id.edit_text_comment)
    }

    // Метод для получения данных, переданных из предыдущей активности (какая кнопка была нажата)
    private fun retrieveExtras() {
        // Получаем значение, переданное с предыдущей активности, с помощью ключа BUTTON_CLICKED_KEY
        buttonClicked = intent.getStringExtra(BUTTON_CLICKED_KEY)
    }

    // Метод для настройки кнопки отправки комментария
    private fun setupButtonSubmit() {
        buttonSubmit.setOnClickListener {
            if (editTextComment.text.isEmpty()) {
                // Если комментарий пустой, показываем сообщение об ошибке
                editTextComment.error = getString(R.string.required_field_str)
                return@setOnClickListener
            }

            // Создаём намерение для запуска следующей активности (PhotoSelectionActivity)
            val intent = Intent(this, PhotoSelectionActivity::class.java).apply {
                // Передаём в новую активность информацию о нажатой кнопке
                putExtra(BUTTON_CLICKED_KEY, buttonClicked)
            }
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
}
