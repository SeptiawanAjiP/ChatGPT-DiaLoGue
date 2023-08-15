package com.dewakoding.dialogue.ui.setting

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dewakoding.dialogue.App
import com.dewakoding.dialogue.R
import com.dewakoding.dialogue.databinding.ActivitySettingBinding
import com.dewakoding.dialogue.util.CommonCons
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
    private val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
    private val viewModel: SettingViewModel by viewModels()

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

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.switchAutoSpeech.isChecked = viewModel.isAutoSpeech()

        binding.switchAutoSpeech.setOnCheckedChangeListener{ _, isChecked ->

            if (isChecked)
                viewModel.setAutoSpeech(true)
            else
                viewModel.setAutoSpeech(false)
        }
    }
}