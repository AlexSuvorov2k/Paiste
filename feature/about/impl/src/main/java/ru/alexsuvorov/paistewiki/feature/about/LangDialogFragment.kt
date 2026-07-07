package ru.alexsuvorov.paistewiki.feature.about

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import ru.alexsuvorov.paistewiki.core.database.AppDatabase
import ru.alexsuvorov.paistewiki.core.support.AppParams
import ru.alexsuvorov.paistewiki.core.support.AppParams.getLangLabel
import ru.alexsuvorov.paistewiki.core.support.BaseViewModelFactory
import ru.alexsuvorov.paistewiki.feature.about.model.AboutIntent
import ru.alexsuvorov.paistewiki.tools.AppPreferences

class LangDialogFragment : DialogFragment() {

    private var rootView: View? = null
    private var radioGroup: RadioGroup? = null

    private val viewModel: AboutViewModel by viewModels {
        BaseViewModelFactory { AboutViewModel(AppPreferences(requireContext())) }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        rootView = requireActivity().layoutInflater.inflate(R.layout.dialog_lang, null)
        radioGroup = rootView!!.findViewById(R.id.radiogroup_theme)

        val currentLocales = AppCompatDelegate.getApplicationLocales()
        val currentLang = if (!currentLocales.isEmpty) {
            currentLocales.get(0)?.language ?: ""
        } else {
            AppParams.setLocale("")
        }

        for ((count, lang) in AppParams.LANG.withIndex()) {
            val radioButton = RadioButton(requireContext())
            radioButton.textSize = 16f
            radioButton.text = getLangLabel(requireContext(), count)
            radioButton.setTextColor(requireContext().getColor(android.R.color.black))
            radioButton.tag = lang
            radioGroup!!.addView(radioButton)

            if (currentLang == lang) {
                radioButton.isChecked = true
            }
        }

        return AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.choose_lang))
            .setPositiveButton(getString(R.string.button_ok)) { _, _ ->
                val selectedId = radioGroup!!.checkedRadioButtonId
                if (selectedId != -1) {
                    val selectedView = radioGroup!!.findViewById<RadioButton>(selectedId)
                    val selectedLang = selectedView.tag as String
                    
                    // Send Intent to ViewModel
                    viewModel.handleIntent(AboutIntent.ChangeLanguage(selectedLang))

                    // System level changes (Side Effects)
                    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(selectedLang)
                    AppCompatDelegate.setApplicationLocales(appLocale)

                    AppDatabase.closeDatabase(requireContext())

                    val splashClass = Class.forName("ru.alexsuvorov.paistewiki.SplashActivity")
                    val refresh = Intent(requireContext(), splashClass)
                    refresh.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(refresh)
                    requireActivity().finish()
                }
            }
            .setNegativeButton(getString(R.string.button_cancel)) { dialog, _ -> dialog.dismiss() }
            .setView(rootView)
            .create()
    }
}
