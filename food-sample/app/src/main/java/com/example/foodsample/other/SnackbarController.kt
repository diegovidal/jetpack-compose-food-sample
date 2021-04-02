package com.example.foodsample.other

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackbarController(
    private val scope: CoroutineScope
) {

    private var snackbarJob: Job? = null

    init {
        cancelActiveJob()
    }

    fun getScope() = scope

    fun showSnackbar(
        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String
    ) {

        if (snackbarJob == null)
            showSnackbarHandler(scaffoldState, message, actionLabel)
        else {
            cancelActiveJob()
            showSnackbarHandler(scaffoldState, message, actionLabel)
        }
    }

    private fun showSnackbarHandler(
        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String
    ) {

        snackbarJob = scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel
            ) //wait
            cancelActiveJob()
        }
    }

    private fun cancelActiveJob() {
        snackbarJob?.let { job ->
            job.cancel()
            snackbarJob = Job()
        }
    }
}