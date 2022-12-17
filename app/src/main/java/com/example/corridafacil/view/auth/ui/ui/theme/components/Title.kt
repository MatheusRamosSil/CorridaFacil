package com.example.corridafacil.view.auth.ui.ui.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Title(title:String, description:String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ){
        Text(text = title,
            style = MaterialTheme.typography.h1)
        Text(text = description,
            style = MaterialTheme.typography.overline)
    }
}