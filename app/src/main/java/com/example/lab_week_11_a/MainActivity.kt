package com.example.lab_week_11_a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_11_a.dataStore.SettingsApplication
import com.example.lab_week_11_a.dataStore.SettingsViewModel
import com.example.lab_week_11_a.sharedPreference.PreferenceApplication
import com.example.lab_week_11_a.sharedPreference.PreferenceViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //stored data used SharedPreference
        //val preferenceWrapper = (application as PreferenceApplication).preferenceWrapper
        //val preferenceViewModel = ViewModelProvider(this, object :
            //ViewModelProvider.Factory {
            //override fun <T : ViewModel> create(modelClass: Class<T>): T {
                //return PreferenceViewModel(preferenceWrapper) as T
            //}
        //})[PreferenceViewModel::class.java]
        //preferenceViewModel.getText().observe(this
        //) {
            //findViewById<TextView>(
                //R.id.activity_main_text_view
            //).text = it
        //}

        // Stored data used dataStore
        val preferenceWrapper = (application as SettingsApplication).settingsStore
        // Create the view model instance with the preference wrapper as the constructor parameter
        // To pass the preference wrapper to the view model, we need to use a view model factory
        val preferenceViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SettingsViewModel(preferenceWrapper) as T
            }
        })[SettingsViewModel::class.java]

        // Observe the text live data
        preferenceViewModel.textLiveData.observe(this) {
            // Update the text view when the text live data changes
            findViewById<TextView>(
                R.id.activity_main_text_view
            ).text = it
        }
        findViewById<Button>(R.id.activity_main_button).setOnClickListener {
            // Save the text when the button is clicked
            preferenceViewModel.saveText(
                findViewById<EditText>(R.id.activity_main_edit_text)
                    .text.toString()
            )
        }
    }
}
