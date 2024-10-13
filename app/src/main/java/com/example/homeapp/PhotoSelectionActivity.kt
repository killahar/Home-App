package com.example.homeapp

// Импортируем необходимые классы для работы
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Определяем класс PhotoSelectionActivity, который позволяет пользователю выбрать фотографии
class PhotoSelectionActivity : ComponentActivity() {

    // Объявляем константы для работы с выбором изображений
    companion object {
        const val REQUEST_IMAGE_SELECT = 100  // Код запроса для выбора изображения
        const val MAX_IMAGE_COUNT = 10  // Максимальное количество изображений
        const val BUTTON_CLICKED_KEY = "button_clicked"  // Ключ для передачи данных о нажатой кнопке
    }

    // Список для хранения выбранных изображений
    private val selectedImages = mutableListOf<Uri>()
    // Адаптер для отображения изображений в RecyclerView
    private lateinit var photoAdapter: PhotoAdapter
    // Переменная для хранения информации о том, какая кнопка была нажата в предыдущей активности
    private var buttonClicked: String? = null
    // Объявляем переменные для элементов интерфейса
    private lateinit var buttonSubmit: Button  // Кнопка для отправки
    private lateinit var backButton: ImageButton  // Кнопка для возврата
    private lateinit var attachPhotosButton: ImageButton  // Кнопка для прикрепления фото
    private lateinit var recyclerView: RecyclerView  // Элемент для отображения фотографий

    // Метод, который вызывается при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливаем макет активности из XML-файла (activity_photo_selection)
        setContentView(R.layout.activity_photo_selection)

        // Инициализируем элементы пользовательского интерфейса
        initViews()
        // Получаем данные о том, какая кнопка была нажата в предыдущей активности
        retriveExtras()
        // Настраиваем действия для кнопок и другие элементы интерфейса
        setupViews()
        // Настраиваем RecyclerView для отображения выбранных фотографий
        setupRecyclerView()
    }

    // Метод для инициализации всех элементов интерфейса
    private fun initViews() {
        buttonSubmit = findViewById(R.id.button_submit)  // Находим кнопку отправки по её ID
        backButton = findViewById(R.id.back_btn)  // Находим кнопку возврата по её ID
        attachPhotosButton = findViewById(R.id.button_attach_photos)  // Находим кнопку прикрепления фото
        recyclerView = findViewById(R.id.recycler_view_photos)  // Находим RecyclerView для фото
    }

    // Метод для получения данных, переданных из предыдущей активности
    private fun retriveExtras() {
        // Получаем значение, переданное с предыдущей активности, с помощью ключа BUTTON_CLICKED_KEY
        buttonClicked = intent.getStringExtra(BUTTON_CLICKED_KEY)
    }

    // Метод для настройки кнопок и других элементов интерфейса
    private fun setupViews() {
        // Настраиваем действие кнопки отправки
        buttonSubmit.setOnClickListener {
            // Переход на следующий экран с передачей информации о нажатой кнопке
            navigateToNextScreen(buttonClicked)
        }

        // Настраиваем действие кнопки прикрепления фото
        attachPhotosButton.setOnClickListener {
            // Запускаем выбор изображений
            selectImages()
        }

        // Настраиваем действие кнопки возврата
        backButton.setOnClickListener {
            // Завершаем текущую активность и возвращаемся на предыдущий экран
            finish()
        }
    }

    // Метод для выбора изображений
    private fun selectImages() {
        // Проверяем, не превышено ли максимальное количество выбранных изображений
        if (selectedImages.size >= MAX_IMAGE_COUNT) {
            // Если превышено, показываем уведомление
            showMaxImagesToast()
        } else {
            // Если нет, открываем диалог для выбора изображений
            Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"  // Указываем, что выбираем изображения
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)  // Разрешаем множественный выбор
            }.also {
                startActivityForResult(it, REQUEST_IMAGE_SELECT)  // Запускаем выбор с указанным кодом запроса
            }
        }
    }

    // Метод для показа уведомления, если пользователь пытается выбрать больше 10 изображений
    private fun showMaxImagesToast() {
        Toast.makeText(this, R.string.attach_photos_toast_str, Toast.LENGTH_SHORT).show()
    }

    // Метод для настройки RecyclerView (списка изображений)
    private fun setupRecyclerView() {
        recyclerView.apply {
            // Устанавливаем горизонтальную прокрутку изображений
            layoutManager = LinearLayoutManager(this@PhotoSelectionActivity, LinearLayoutManager.HORIZONTAL, false)
            // Создаём адаптер для фотографий и настраиваем его
            photoAdapter = PhotoAdapter(this@PhotoSelectionActivity, selectedImages) { uri -> removeImage(uri) }
            adapter = photoAdapter  // Применяем адаптер к RecyclerView
        }
    }

    // Метод для удаления выбранного изображения
    private fun removeImage(uri: Uri) {
        selectedImages.remove(uri)  // Удаляем изображение из списка
        photoAdapter.notifyDataSetChanged()  // Обновляем адаптер, чтобы изменения отобразились
    }

    // Метод, который вызывается, когда результат выбора изображения возвращён в активность
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Проверяем, что результат выбора изображения успешен
        if (requestCode == REQUEST_IMAGE_SELECT && resultCode == Activity.RESULT_OK) {
            handleSelectedImages(data)  // Обрабатываем выбранные изображения
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // Метод для обработки выбранных изображений
    private fun handleSelectedImages(data: Intent?) {
        // Если выбрано несколько изображений
        data?.clipData?.let { clipData ->
            // Перебираем все выбранные изображения
            for (i in 0 until clipData.itemCount) {
                val uri = clipData.getItemAt(i).uri  // Получаем URI изображения
                // Добавляем изображение в список, если его ещё нет
                if (!selectedImages.contains(uri)) {
                    selectedImages.add(uri)
                }
            }
            // Если выбрано одно изображение
        } ?: data?.data?.let { uri ->
            // Добавляем изображение в список, если его ещё нет
            if (!selectedImages.contains(uri)) {
                selectedImages.add(uri)
            }
        }

        // Обновляем адаптер для отображения добавленных изображений
        photoAdapter.notifyDataSetChanged()
    }

    // Метод для перехода на следующий экран
    private fun navigateToNextScreen(buttonClicked: String?) {
        // Проверяем, выбрал ли пользователь хотя бы одно изображение
        if (selectedImages.isEmpty()) {
            // Если нет, показываем уведомление
            Toast.makeText(this, "Выберите хотя бы одну фотографию", Toast.LENGTH_SHORT).show()
        } else {
            // Если выбраны изображения, определяем, на какой экран переходить
            val nextActivity = when (buttonClicked) {
                "request_button" -> EndAuthorizationActivity::class.java  // Для запроса
                "appointment_button" -> DateSelectionActivity::class.java  // Для встречи
                else -> ApplicationMalfunctionActivity::class.java  // Если произошла ошибка
            }
            // Создаём намерение для перехода и передаём данные о нажатой кнопке
            val intent = Intent(this, nextActivity).apply {
                putExtra(BUTTON_CLICKED_KEY, buttonClicked)
            }
            // Запускаем следующую активность
            startActivity(intent)
        }
    }
}
