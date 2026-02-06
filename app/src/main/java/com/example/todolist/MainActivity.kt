package com.example.todolist

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.today.TodayFragment
import dagger.hilt.android.AndroidEntryPoint

private const val FRAGMENT_TAG = "todayFragment"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var fragment: TodayFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment = if (savedInstanceState == null) {
            TodayFragment()
        } else {
            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as TodayFragment
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.today_fragment_container, fragment, FRAGMENT_TAG)
            .commit()

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStack()
                    } else {
                        finish()
                    }
                }
            })
    }
}