package com.example.proyectoappmobile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoappmobile.model.User
import com.example.proyectoappmobile.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList: StateFlow<List<User>> = _userList

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _statusMessage = MutableStateFlow<String>("")
    val statusMessage: StateFlow<String> = _statusMessage

    fun addUser(user: User) {
        viewModelScope.launch {
            val result = userRepository.addUser(user)
            if (result.isSuccess) {
                _statusMessage.value = "User added successfully"
                getAllUsers()
            } else {
                _statusMessage.value = "Failed to add user: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    fun getUserById(userId: String) {
        viewModelScope.launch {
            val result = userRepository.getUserById(userId)
            if (result.isSuccess) {
                _currentUser.value = result.getOrNull()
                _statusMessage.value = "User retrieved successfully"
            } else {
                _statusMessage.value = "Failed to retrieve user: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    fun getAllUsers() {
        viewModelScope.launch {
            val result = userRepository.getAllUsers()
            if (result.isSuccess) {
                _userList.value = result.getOrNull() ?: emptyList()
                _statusMessage.value = "Users retrieved successfully"
            } else {
                _statusMessage.value = "Failed to retrieve users: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            val result = userRepository.updateUser(user)
            if (result.isSuccess) {
                _statusMessage.value = "User updated successfully"
                getAllUsers() // Refresca la lista de usuarios
            } else {
                _statusMessage.value = "Failed to update user: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            val result = userRepository.deleteUser(userId)
            if (result.isSuccess) {
                _statusMessage.value = "User deleted successfully"
                getAllUsers() // Refresca la lista de usuarios
            } else {
                _statusMessage.value = "Failed to delete user: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    fun updateStatusMessage(message: String) {
        _statusMessage.value = message
    }
}
