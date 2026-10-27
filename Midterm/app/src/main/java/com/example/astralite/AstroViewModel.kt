
package com.example.astralite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AstroViewModel : ViewModel() {

    private val _moodHistory =
        MutableStateFlow<List<MoodEntry>>(emptyList()) //хранит текущее значение списка и позволяет другим частям приложения
            //наблюдать за его изменениями

    val moodHistory = _moodHistory.asStateFlow() // доступно только чтение

    fun saveMood(mood: String) {
        viewModelScope.launch {
            val date = SimpleDateFormat(
                "dd MMM yyyy, HH:mm",
                Locale.ENGLISH
            ).format(Date())

            val entry = MoodEntry( //создание новой записи
                id = System.currentTimeMillis(),
                mood = mood,
                date = date
            )

            _moodHistory.value = _moodHistory.value + entry //добавление в историю
        }
    }

    fun deleteMood(id: Long) {
        viewModelScope.launch {
            _moodHistory.value =
                _moodHistory.value.filter { it.id != id }
        }
    }
}
