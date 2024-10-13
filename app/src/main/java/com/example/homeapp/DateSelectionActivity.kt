package com.example.homeapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.util.Calendar

// Определение класса активности выбора даты
class DateSelectionActivity : ComponentActivity() {

    // Константа для передачи информации о нажатой кнопке
    companion object {
        const val BUTTON_CLICKED_KEY = "button_clicked"
    }

    // Переменная для хранения данных о том, какая кнопка была нажата в предыдущей активности
    private var buttonClicked: String? = null
    // Переменные для отслеживания выбранной даты и времени
    private var selectedDate: String? = null
    private var selectedTimeSlot: String? = null
    // Объявление текстовых полей и кнопок, которые будут использоваться в интерфейсе
    private lateinit var selectedDateTextView: TextView
    private lateinit var selectedTimeSlotTextView: TextView
    private lateinit var setDateButton: ImageButton
    private lateinit var morningButton: Button
    private lateinit var daytimeButton: Button
    private lateinit var eveningButton: Button
    private lateinit var buttonSubmit: Button
    private lateinit var backButton: ImageButton

    // Метод, который вызывается при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_selection)  // Устанавливаем макет активности

        initViews()  // Инициализируем элементы интерфейса
        retrieveExtras()  // Получаем данные, переданные из предыдущей активности
        setupViews()  // Настраиваем действия кнопок
    }

    // Метод для инициализации элементов пользовательского интерфейса
    private fun initViews() {
        selectedDateTextView = findViewById(R.id.textView4)  // Текстовое поле для выбранной даты
        selectedTimeSlotTextView = findViewById(R.id.textView5)  // Текстовое поле для выбранного временного интервала
        setDateButton = findViewById(R.id.set_date)  // Кнопка для выбора даты
        morningButton = findViewById(R.id.morning)  // Кнопка для выбора утреннего интервала
        daytimeButton = findViewById(R.id.daytime)  // Кнопка для выбора дневного интервала
        eveningButton = findViewById(R.id.evening)  // Кнопка для выбора вечернего интервала
        backButton = findViewById(R.id.back_btn)  // Кнопка для возврата
        buttonSubmit = findViewById(R.id.button_submit)  // Кнопка для подтверждения выбора
    }

    // Метод для получения информации о нажатой кнопке из предыдущей активности
    private fun retrieveExtras() {
        buttonClicked = intent.getStringExtra(BUTTON_CLICKED_KEY)  // Получаем информацию о нажатой кнопке
    }

    // Метод для настройки действий кнопок
    private fun setupViews() {
        // Устанавливаем действие на кнопку выбора даты
        setDateButton.setOnClickListener {
            showDatePickerDialog()  // Открываем диалог выбора даты
        }

        // Устанавливаем действия на кнопки выбора временных интервалов
        morningButton.setOnClickListener {
            showTimeSlotDialog("morning")  // Открываем диалог для выбора утреннего интервала
        }

        daytimeButton.setOnClickListener {
            showTimeSlotDialog("daytime")  // Открываем диалог для выбора дневного интервала
        }

        eveningButton.setOnClickListener {
            showTimeSlotDialog("evening")  // Открываем диалог для выбора вечернего интервала
        }

        // Настраиваем кнопку возврата
        backButton.setOnClickListener {
            finish()  // Завершаем текущую активность и возвращаемся на предыдущий экран
        }

        // Настраиваем кнопку подтверждения выбора
        buttonSubmit.setOnClickListener {
            navigateToNextScreen()  // Переходим на следующий экран
        }
    }

    // Метод для отображения диалога выбора временного интервала (утро, день, вечер)
    private fun showTimeSlotDialog(timeOfDay: String) {
        // Определяем временные интервалы в зависимости от времени дня
        val timeSlots = when (timeOfDay) {
            "morning" -> resources.getStringArray(R.array.morning_time_slots)  // Утренние временные интервалы
            "daytime" -> resources.getStringArray(R.array.daytime_time_slots)  // Дневные интервалы
            "evening" -> resources.getStringArray(R.array.evening_time_slots)  // Вечерние интервалы
            else -> emptyArray()  // Пустой массив, если что-то пошло не так
        }

        // Создаём диалог для выбора временного интервала
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle(R.string.select_available_time_slot_str)  // Заголовок диалога
        builder.setItems(timeSlots) { _, which ->  // Устанавливаем действие для выбранного интервала
            setupSelectedTimeSlot(timeSlots[which])  // Передаём выбранный интервал в метод
        }
        builder.show()  // Отображаем диалог
    }

    // Метод для отображения выбранного временного интервала в интерфейсе
    private fun setupSelectedTimeSlot(timeSlot: String) {
        selectedTimeSlot = timeSlot // Сохраняем выбранный временной интервал
        selectedTimeSlotTextView.text = getString(R.string.selected_appointment_time_slot_str, timeSlot)  // Обновляем текст с выбранным интервалом
        selectedTimeSlotTextView.textSize = 20F  // Устанавливаем размер текста
    }

    // Метод для отображения диалога выбора даты
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()  // Получаем текущую дату
        // Создаём диалог выбора даты
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.CustomDatePickerDialog,  // Устанавливаем стиль диалога
            { _, year, month, day ->  // Обрабатываем выбранную дату
                setupSelectedDate(day, month, year)  // Передаём выбранную дату в метод
            },
            calendar.get(Calendar.YEAR),  // Текущий год
            calendar.get(Calendar.MONTH),  // Текущий месяц
            calendar.get(Calendar.DAY_OF_MONTH)  // Текущий день
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis  // Устанавливаем минимальную дату (сегодня)

        datePickerDialog.show()  // Отображаем диалог выбора даты
    }

    // Метод для обновления текстового поля с выбранной датой
    private fun setupSelectedDate(day: Int, month: Int, year: Int) {
        // Форматируем дату в строку (dd.MM.yyyy)
        selectedDate = String.format("%02d.%02d.%04d", day, month + 1, year)
        // Обновляем текстовое поле с выбранной датой
        selectedDateTextView.text = getString(R.string.selected_appointment_date_str, selectedDate)
        selectedDateTextView.textSize = 20F  // Устанавливаем размер текста
    }

    // Метод для перехода на следующий экран после выбора даты и времени
    private fun navigateToNextScreen() {
        // Проверяем, выбраны ли дата и временной интервал
        if (selectedDate == null || selectedTimeSlot == null) {
            Toast.makeText(this, R.string.select_date_and_time_slot_str, Toast.LENGTH_SHORT).show()  // Показываем сообщение об ошибке
            return  // Прерываем выполнение, если нет выбора
        }

        // Определяем, какая следующая активность будет запущена, в зависимости от нажатой кнопки
        val nextActivity = when (buttonClicked) {
            "request_button", "appointment_button" -> EndAuthorizationActivity::class.java  // Для запроса или встречи
            else -> ApplicationMalfunctionActivity::class.java  // Если произошла ошибка
        }

        // Создаём намерение для перехода на следующую активность
        val intent = Intent(this, nextActivity).apply {
            putExtra(BUTTON_CLICKED_KEY, buttonClicked)  // Передаём данные о нажатой кнопке
        }
        startActivity(intent)  // Запускаем следующую активность
    }
}
