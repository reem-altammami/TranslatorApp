package com.reem.translatorapp.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.reem.translatorapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val languages: MutableMap<String, String> = emptyMap<String, String>().toMutableMap()
    private lateinit var translateFrom: String
    private lateinit var translateTo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeSupportedLang()
        observeTranslate()
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onStart() {
        super.onStart()
        binding.translateFromSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    val key = languages.keys.toList()[p2]
                    translateFrom = key
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        binding.translateToSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    val key = languages.keys.toList()[p2]
                    translateTo = key
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

        binding.translateBtn.setOnClickListener {
            if (translateTo.isNotBlank() && translateFrom.isNotBlank() && it.toString().isNotBlank()){
                viewModel.translate(binding.inputTxtView.text.toString(), translateTo, translateFrom)
            }
        }

        binding.switchLanImgBtn.setOnClickListener {
            changLang()
        }
    }

    private fun observeSupportedLang() {
        lifecycleScope.launchWhenCreated {
            viewModel.supportLangState.collect { result ->
                when {
                    result.isLoading -> {
                        Log.d(TAG, "Loading: ${result.isLoading}")
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    result.data != null -> {
                        Log.d(TAG, "Success: ${result.data}")
                        binding.progressBar.visibility = View.GONE
                        result.data.data?.forEach {
                            languages[it.value] = it.key
                        }
                        setToSpinner(languages.keys.toMutableList())
                        setFromSpinner(languages.keys.toMutableList())
                    }
                    result.message.isNotEmpty() -> {
                        Log.d(TAG, "Error: ${result.message}")
                        binding.progressBar.visibility = View.GONE
                        Snackbar.make(binding.root, result.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun observeTranslate() {
        lifecycleScope.launchWhenCreated {
            viewModel.translateState.collect { result ->
                when {
                    result.isLoading -> {
                        binding.progressBar.visibility = View.VISIBLE

                    }
                    result.data != null -> {
                        binding.progressBar.visibility = View.GONE
                        binding.resultTxtView.visibility = View.VISIBLE
                        binding.resultTxtView.text = result.data.data.translation
                        Log.d(TAG, "observeTranslate: ${result.data}")
                        if (result.data.data.corrected.didYouMean){
                            binding.didYouMeanLabelTxtView.visibility = View.VISIBLE
                            binding.didYouMeanTxtView.visibility = View.VISIBLE
                            binding.didYouMeanTxtView.text = result.data.data.corrected.value
                        }
                    }
                    result.message.isNotEmpty() -> {
                        binding.progressBar.visibility = View.GONE
                        Snackbar.make(binding.root, result.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setToSpinner(supportedLang: MutableList<String>) {
        supportedLang[0] = "To"
        val translateToAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            supportedLang
        )
        binding.translateToSpinner.adapter = translateToAdapter
    }

    private fun setFromSpinner(supportedLang: MutableList<String>) {
        supportedLang[0] = "From"
        val translateFromAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            supportedLang
        )
        binding.translateFromSpinner.adapter = translateFromAdapter
    }
    fun getValue(key: String): String {
        return languages[key] ?: ""
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun changLang(){
        var temp = translateFrom
        translateFrom = translateTo
        translateTo = temp

        val temp2 = binding.translateFromSpinner.selectedItemPosition
        binding.translateFromSpinner.setSelection(binding.translateToSpinner.selectedItemPosition)
        binding.translateToSpinner.setSelection(temp2)

        val text = binding.resultTxtView.text.toString()
        binding.inputTxtView.setText(text)
        viewModel.translate(text, translateTo, translateFrom)
    }
}