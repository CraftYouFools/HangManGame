package com.remid.hangmangame.di.activity

import com.remid.hangmangame.di.presentation.PresentationComponent
import com.remid.hangmangame.di.presentation.PresentationModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent
}