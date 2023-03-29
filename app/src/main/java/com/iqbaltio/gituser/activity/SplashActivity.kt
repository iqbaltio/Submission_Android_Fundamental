package com.iqbaltio.gituser.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.iqbaltio.gituser.R
import com.iqbaltio.gituser.database.SettingPreferences
import com.iqbaltio.gituser.viewmodel.SettingViewModel
import com.iqbaltio.gituser.viewmodel.VMFactorySetting

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val stay = 3000

        val pref = SettingPreferences.getInstance(dataStore)
        val mViewModels = ViewModelProvider(this, VMFactorySetting(pref))[SettingViewModel::class.java]
        mViewModels.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                moveToMain(stay)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                moveToMain(stay)
            }
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({

        }, 3000)
    }

    private fun moveToMain(stay: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, stay.toLong())
    }
}