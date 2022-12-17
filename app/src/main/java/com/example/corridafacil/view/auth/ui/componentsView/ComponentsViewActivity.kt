package com.example.corridafacil.view.auth.ui.componentsView

import android.app.Activity
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class ComponentsViewActivity(val activity: Activity) {


    fun getValueFieldEdtiTextToString(componentId: Int) = activity.findViewById<EditText>(componentId).text.toString()

    fun getComponentImageView(componentId: Int) = activity.findViewById<ImageView>(componentId)

    fun getComponentTextView(componentId: Int) = activity.findViewById<TextView>(componentId)

    fun getComponentEditText(componentId: Int) = activity.findViewById<EditText>(componentId)

}