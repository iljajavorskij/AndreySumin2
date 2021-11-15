package org.myapp.andreysumin2.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.myapp.andreysumin2.domain.entity.Level
import java.lang.RuntimeException

class GameViewModelFactory(
    private val application:Application,
    private val level: Level
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameFragmentViewModel::class.java)){
            return GameFragmentViewModel(application,level) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}