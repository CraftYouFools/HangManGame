package com.remid.hangmangame.shared.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : BaseActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        presentationInjector.inject(this)
        super.onCreate(savedInstanceState)
    }
}
