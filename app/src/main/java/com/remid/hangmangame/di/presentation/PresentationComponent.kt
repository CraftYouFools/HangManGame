package com.remid.hangmangame.di.presentation


import com.remid.hangmangame.shared.presentation.activity.MainActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(activity : MainActivity)
}