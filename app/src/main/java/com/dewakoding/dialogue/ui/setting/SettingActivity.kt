package com.dewakoding.dialogue.ui.setting

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dewakoding.dialogue.App
import com.dewakoding.dialogue.databinding.ActivityApiKeyBinding
import com.dewakoding.dialogue.preference.CommonCons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**

Created by
name : Septiawan Aji Pradana
email : septiawanajipradana@gmail.com
website : dewakoding.com

 **/
class SettingActivity: AppCompatActivity() {
    private val binding by lazy { ActivityApiKeyBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (!App.getSession().getSessionString(CommonCons.API_KEY).isNullOrEmpty()) {
            binding.etApiKey.setText(App.getSession().getSessionString(CommonCons.API_KEY).toString())
        }

        binding.imgCheck.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                if (!binding.etApiKey.text.isNullOrEmpty()) {
                    App.getSession().createSessionString(CommonCons.API_KEY, binding.etApiKey.text.toString())
                    runOnUiThread{
                        Toast.makeText(applicationContext, "Success, API KEY saved", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Empty Api Key", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}