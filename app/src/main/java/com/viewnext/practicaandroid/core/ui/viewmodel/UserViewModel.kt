package com.viewnext.practicaandroid.core.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.viewnext.practicaandroid.core.PracticaAndroidApplication
import com.viewnext.practicaandroid.domain.data.UserDetailsEntity
import com.viewnext.practicaandroid.domain.repository.InvoiceRepository
import com.viewnext.practicaandroid.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    data class UiState(
        val loading : Boolean = false,
        val error : String? = null,
        val userDetails : UserDetailsEntity = UserDetailsEntity()
    )

    private var _state = MutableStateFlow(UiState())
    val state : StateFlow<UiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            try {
                val userDetails = repository.getUserDetails()
                Log.d("UserDetails", userDetails.toString())
                _state.update { it.copy(userDetails = userDetails) }
            } catch (e: Exception) {
                Log.e("ErrorType", e::class.simpleName ?: "Unknown")
                Log.e("Error", e.message.toString())
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as PracticaAndroidApplication)
                val repository: UserRepository = application.container.userRepository
                UserViewModel(repository)
            }
        }
    }
}