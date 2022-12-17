package com.example.corridafacil.view.auth.ui.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Button(heigh: Dp, width: Dp, click: ()-> Unit){

    Box(modifier = Modifier.padding(top = 26.dp)) {
        androidx.compose.material.Button(onClick = {
                click()
        },
            colors= ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
            modifier = Modifier
                .height(heigh)
                .width(width)
        ) {
            Text("Sign in", style = MaterialTheme.typography.overline, color = Color.White, fontSize = 14.sp)
        }
    }

}