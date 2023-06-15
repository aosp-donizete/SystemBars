package com.android.systembars

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.postDelayed

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        //applied as XML resource
        //window.applyCutout()

        //startWindowIssue(false)
        removeStatusBar()
    }

    private fun startWindowIssue(state: Boolean) {
        Toast.makeText(this, "State: $state", Toast.LENGTH_SHORT).show()
        if (state) {
            addStatusBar()
        } else {
            removeStatusBar()
        }
        window.decorView.postDelayed(5000) {
            startWindowIssue(!state)
        }
    }

    private inline fun onWindowInsetsControllerCompat(
        block: WindowInsetsControllerCompat.() -> Unit
    ) = WindowCompat.getInsetsController(window, window.decorView).apply(block)

    fun removeStatusBar(v: View) = removeStatusBar()

    private fun removeStatusBar() = onWindowInsetsControllerCompat {
        isAppearanceLightStatusBars = false
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        hide(WindowInsetsCompat.Type.systemBars())
    }

    fun addStatusBar(v: View) = addStatusBar()

    private fun addStatusBar() = onWindowInsetsControllerCompat {
        isAppearanceLightStatusBars = false
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        hide(0)
    }
}

fun Window.applyCutout() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }
}