package com.remid.hangmangame.shared.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import com.remid.hangmangame.HangmanApplication
import com.remid.hangmangame.di.presentation.PresentationComponent
import com.remid.hangmangame.di.presentation.PresentationModule
import com.remid.hangmangame.di.activity.ActivityModule

open class BaseActivity: AppCompatActivity() {

    private val appComponent get() = (application as HangmanApplication).appComponent


    val activityComponent by lazy {
        appComponent.newActivityComponent(ActivityModule(this))
    }

    private val presentationComponent by lazy {
        activityComponent.newPresentationComponent(PresentationModule())
    }

    protected val presentationInjector: PresentationComponent get() = presentationComponent


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}