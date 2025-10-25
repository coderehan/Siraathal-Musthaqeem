package com.islamicapp.siraathalmusthaqeem.screen.tasbeeh

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.screen.tasbeeh.model.TasbeehData
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasbeehViewModel(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _tasbeehState = MutableStateFlow(TasbeehData())
    val tasbeehState: StateFlow<TasbeehData> = _tasbeehState

    private var soundPool: SoundPool? = null
    private var soundId: Int? = null

    fun init(context: Context) {
        // Initialize SoundPool
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .build()

        soundId = soundPool?.load(context, R.raw.tasbeeh_click, 1)

        // Load persisted data
        viewModelScope.launch {
            preferencesManager.tasbeehDataFlow.collectLatest { data ->
                _tasbeehState.value = data
            }
        }
    }

    fun incrementCount(context: Context) {
        val current = _tasbeehState.value
        val updated = current.copy(count = current.count + 1)
        _tasbeehState.value = updated
        viewModelScope.launch { preferencesManager.saveTasbeehData(updated) }

        viewModelScope.launch {
            playFeedback(context, current.soundEnabled, current.vibrationEnabled)
        }
    }

    fun resetTasbeeh() {
        val updated = _tasbeehState.value.copy(count = 0)
        _tasbeehState.value = updated
        viewModelScope.launch { preferencesManager.saveTasbeehData(updated) }
    }

    fun updateTarget(newTarget: Int) {
        val updated = _tasbeehState.value.copy(target = newTarget)
        _tasbeehState.value = updated
        viewModelScope.launch { preferencesManager.saveTasbeehData(updated) }
    }

    fun toggleSound() {
        val updated = _tasbeehState.value.copy(soundEnabled = !_tasbeehState.value.soundEnabled)
        _tasbeehState.value = updated
        viewModelScope.launch { preferencesManager.saveTasbeehData(updated) }
    }

    fun toggleVibration() {
        val updated = _tasbeehState.value.copy(vibrationEnabled = !_tasbeehState.value.vibrationEnabled)
        _tasbeehState.value = updated
        viewModelScope.launch { preferencesManager.saveTasbeehData(updated) }
    }

    private suspend fun playFeedback(
        context: Context,
        soundEnabled: Boolean,
        vibrationEnabled: Boolean
    ) {
        withContext(Dispatchers.Default) {
            if (vibrationEnabled) {
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(30)
                }
            }

            if (soundEnabled && soundId != null) {
                soundPool?.play(soundId!!, 1f, 1f, 0, 0, 1f)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundPool?.release()
        soundPool = null
    }
}
