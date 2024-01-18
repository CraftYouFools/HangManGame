package com.remid.hangmangame.di.activity


import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.remid.hangmangame.shared.business.INavigator
import com.remid.hangmangame.shared.presentation.navigation.Navigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun activity() = activity

    @Provides
    fun fragmentManager() = activity.supportFragmentManager

    @Provides
    fun actionBar(): ActionBar? = activity.supportActionBar

    @Provides
    @ActivityScope
    fun navigator(fragmentManager: FragmentManager) : INavigator = Navigator(fragmentManager)

}