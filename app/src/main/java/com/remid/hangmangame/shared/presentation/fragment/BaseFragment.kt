package com.remid.hangmangame.shared.presentation.fragment

import androidx.fragment.app.Fragment
import com.remid.hangmangame.di.presentation.PresentationModule
import com.remid.hangmangame.shared.presentation.activity.BaseActivity


open class BaseFragment : Fragment() {

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent(
            PresentationModule()
        )
    }

    protected val injector get() = presentationComponent

}