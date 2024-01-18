package com.remid.hangmangame.shared.presentation.activity

import android.os.Bundle
import com.remid.hangmangame.R
import com.remid.hangmangame.databinding.ActivityMainBinding
import com.remid.hangmangame.shared.business.INavigator
import javax.inject.Inject

class MainActivity : BaseActivity()  {

    @Inject
    lateinit var navigator: INavigator

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presentationInjector.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar = binding.toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        toolbar.title= getString(R.string.hangman_game)

        setSupportActionBar(toolbar)

        navigator.attachHangmanGame()
    }
}
