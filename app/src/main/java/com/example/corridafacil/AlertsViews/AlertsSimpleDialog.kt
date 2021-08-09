package com.example.corridafacil.AlertsViews

import android.app.Activity
import android.app.AlertDialog
import com.example.corridafacil.R

class AlertsSimpleDialog (private val activity: Activity){


    lateinit var dialog:AlertDialog

    fun startLoadingDialog(){
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.alert_load_sms,null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun dimissDialog(){
        dialog!!.dismiss()
    }


}