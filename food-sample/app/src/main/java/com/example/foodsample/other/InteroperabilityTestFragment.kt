package com.example.foodsample.other

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.example.foodsample.R

class InteroperabilityTestFragment : Fragment(R.layout.fragment_test_interoperability) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ComposeView>(R.id.cv_custom_view).setContent {
            RenderScreen()
        }
    }

    @Preview
    @Composable
    private fun RenderScreen() {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "This is a composable inside the fragment")
            Spacer(modifier = Modifier.padding(10.dp))
            CircularProgressIndicator()
            Spacer(modifier = Modifier.padding(16.dp))

            val customView = HorizontalDottedProgress(requireContext())
            AndroidView(factory = { customView })

            Spacer(modifier = Modifier.padding(16.dp))

            val customTextView = TextView(requireContext())
            customTextView.text = "Isso é uma textview"
            AndroidView(factory = { customTextView })
        }
    }
}