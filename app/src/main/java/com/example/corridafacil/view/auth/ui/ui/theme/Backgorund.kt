package com.example.corridafacil.view.auth.ui.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.corridafacil.utils.responsive.WindowSize
import com.example.corridafacil.utils.responsive.WindowType
import com.example.corridafacil.utils.responsive.rememberWindowSize


@Composable
fun Background(windowSize: WindowSize) {
    drawBackground(windowSize)


}
@Composable
fun drawBackgroundMedium(windowSize: WindowSize) {
    Canvas(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)){
        val canvasWidth = windowSize.screenWidth.toPx()

        drawCircle(
            brush= Brush.verticalGradient(0f to DarkYellowGradientCircle, 0.30f to DarkYellowGradientCircle200),
            center = Offset(x = (canvasWidth * 0.35).toFloat(), y = 0F),

            radius = (size.minDimension * 0.50).toFloat()
        )
        drawCircle(
            brush= Brush.verticalGradient(0f to YellowGradientCircle, 0.30f to AmberGradientCircle),
            center = Offset(x = 0F, y = 0F),
            radius = (size.minDimension * 0.80).toFloat()
        )

    }
}

@Composable
fun drawBackground(windowSize: WindowSize) {
    Canvas(modifier = Modifier.fillMaxSize()
                               .background(Color.White)){
        val canvasWidth = windowSize.screenWidth.toPx()

        drawCircle(
            brush= Brush.verticalGradient(0f to DarkYellowGradientCircle, 0.30f to DarkYellowGradientCircle200),
            center = Offset(x = (canvasWidth * 0.85).toFloat(), y = 0F),

            radius = (size.minDimension * 0.50).toFloat()
        )
        drawCircle(
            brush= Brush.verticalGradient(0f to YellowGradientCircle, 0.30f to AmberGradientCircle),
            center = Offset(x = 0F, y = -0F),
            radius = (size.minDimension * 0.80).toFloat()
        )

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CorridaFacilTheme {
        val window = rememberWindowSize()
        Background(window)
    }
}