package com.iqbaltio.gituser.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.iqbaltio.gituser.R
import com.iqbaltio.gituser.database.SettingPreferences
import com.iqbaltio.gituser.databinding.ActivitySettingBinding
import com.iqbaltio.gituser.viewmodel.SettingViewModel
import com.iqbaltio.gituser.viewmodel.VMFactorySetting

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Theme Setting"

        val pref = SettingPreferences.getInstance(dataStore)
        val VModels = ViewModelProvider(this, VMFactorySetting(pref))[SettingViewModel::class.java]
        VModels.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }
        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            VModels.saveThemeSetting(isChecked)
        }
    }
}