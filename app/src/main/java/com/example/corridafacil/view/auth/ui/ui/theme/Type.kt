package com.example.corridafacil.view.auth.ui.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.corridafacil.R




// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = CustomFonts().getInterFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp
    ),
    h3 = TextStyle(
        fontFamily = CustomFonts().getInterFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    body2 = TextStyle(
        fontFamily = CustomFonts().getInterFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    body1 = TextStyle(
        fontFamily = CustomFonts().getInterFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    overline = TextStyle(
        fontFamily = CustomFonts().getInterFamily(),
        fontWeight = FontWeight.Light,
        fontSize = 13.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = CustomFonts().getInterFamily(),
        fontWeight = FontWeight.Medium,
        color = ColorFieldText
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

class CustomFonts{
    private val InterFamily = FontFamily(
        Font(R.font.inter_thin, FontWeight.Thin),
        Font(R.font.inter_semibold, FontWeight.SemiBold),
        Font(R.font.inter_regular, FontWeight.Normal),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_extralight, FontWeight.ExtraLight),
        Font(R.font.inter_light, FontWeight.Light),
        Font(R.font.inter_extrabold, FontWeight.ExtraBold),
        Font(R.font.inter_bold, FontWeight.Bold),
        Font(R.font.inter_black, FontWeight.Black)
    )

    @JvmName("getInterFamily1")
    fun getInterFamily(): FontFamily {
        return InterFamily
    }
}