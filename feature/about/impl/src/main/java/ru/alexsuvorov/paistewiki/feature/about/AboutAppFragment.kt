package ru.alexsuvorov.paistewiki.feature.about

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.alexsuvorov.paistewiki.core.design.theme.PaisteTheme
import ru.alexsuvorov.paistewiki.core.support.BaseViewModelFactory
import ru.alexsuvorov.paistewiki.feature.about.compose.AboutScreen
import ru.alexsuvorov.paistewiki.tools.AppPreferences

class AboutAppFragment : Fragment() {

    private val viewModel: AboutViewModel by viewModels {
        BaseViewModelFactory { AboutViewModel(AppPreferences(requireContext())) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                PaisteTheme {
                    AboutScreen(
                        viewModel = viewModel,
                        onLanguageClick = {
                            val dialogFragment: DialogFragment = LangDialogFragment()
                            dialogFragment.show(requireActivity().supportFragmentManager, "dialogFragmentLang")
                        },
                        onHelpClick = {
                            sendEmail()
                        },
                        onVkClick = {
                            openVk()
                        }
                    )
                }
            }
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = "mailto: alexsuvorov2k@gmail.com".toUri()
        intent.putExtra(Intent.EXTRA_SUBJECT, "Paiste Wiki")
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun openVk() {
        val intent = Intent(Intent.ACTION_VIEW, "https://vk.com/paistecymbals".toUri())
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        //requireActivity().setTitle(R.string.nav_header_about)
    }
}
