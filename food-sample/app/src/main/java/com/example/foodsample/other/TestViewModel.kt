package com.example.foodsample.other

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

class SignInViewModel(/* ... */): ViewModel() {

    // Initial state is SignedOut.
    private val _state = MutableLiveData<SignInState>(SignInState.SignedOut)
    val state: LiveData<SignInState> = _state

    fun onSignIn(email: String, password: String) {
        viewModelScope.launch {
            _state.value = SignInState.InProgress
            val result = true
            if(result){
                _state.value = SignInState.SignedIn
            } else {
                _state.value = SignInState.Error("result error")
            }
        }
    }
}

sealed class SignInState {
    object SignedOut : SignInState()
    object InProgress : SignInState()
    data class Error(val error: String) : SignInState()
    object SignedIn : SignInState()
}

@Composable
fun SignIn(viewModel: SignInViewModel = viewModel()) {

    Button(
        onClick = { viewModel.onSignIn("emailState.text", "passwordState.text") }
    ) {
        Text(text = "Sign in")
    }
}

