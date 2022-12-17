package com.example.corridafacil.utils.permissions

import android.os.Build
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.example.corridafacil.view.auth.ui.Login
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PermissionsTest{

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    @Test
    fun verifcar_permissao_de_localizacao(){
        ActivityScenario.launch(Login::class.java)

        grantPermission()
    }




    private fun grantPermission() {
       val instrumentation = InstrumentationRegistry.getInstrumentation()
        if(Build.VERSION.SDK_INT >= 23){
            val allowedPermission = UiDevice.getInstance(instrumentation).findObject(UiSelector().text(
                when{
                    Build.VERSION.SDK_INT == 23 ->"Allow"
                    Build.VERSION.SDK_INT <= 28 -> "ALLOW"
                    Build.VERSION.SDK_INT == 29 -> "Allow only while using the app"
                    else -> "While using the app"
                }
            ))

            if (allowedPermission.exists()){
                allowedPermission.click()
            }
        }
    }
}