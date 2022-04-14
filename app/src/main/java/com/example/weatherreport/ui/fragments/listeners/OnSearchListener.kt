package com.example.weatherreport.ui.fragments.listeners

import android.view.KeyEvent
import android.view.View
import android.widget.EditText

class OnSearchListener(
    private val shift: (name: String) -> Unit
) : View.OnKeyListener {

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if ((event?.action == KeyEvent.ACTION_DOWN) &&
            (keyCode == KeyEvent.KEYCODE_ENTER)
        ) {
            shift.invoke((v as EditText).text.toString())
            return true;
        }
        return false;
    }
}