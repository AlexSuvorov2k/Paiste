package ru.alexsuvorov.paistewiki.fragments

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.alexsuvorov.paistewiki.App
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.tools.AppPreferences

class InstagramViewerFragment : Fragment() {

    val appPreferences : AppPreferences by lazy{
        AppPreferences(requireActivity())
    }

    private var webView: WebView? = null
    private var floatingActionButton: FloatingActionButton? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.application as App).setLocale()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_instagram, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById<WebView>(R.id.web_view)
        webView!!.webViewClient = InstaWebViewClient()
        webView!!.settings.javaScriptEnabled = true
        webView!!.loadUrl("https://www.instagram.com/explore/tags/paistecymbals/")
        floatingActionButton = view.findViewById<FloatingActionButton>(R.id.back_fab)
    }

    inner class InstaWebViewClient : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.url.toString())
            return true
        }

        private fun linkBackButton(flag: Boolean) {
            if (flag) {
                floatingActionButton!!.show()
            } else {
                floatingActionButton!!.hide()
            }

            floatingActionButton!!.setOnClickListener(View.OnClickListener { view: View? ->
                if (webView!!.canGoBack()) webView!!.goBack()
            })
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            linkBackButton(webView!!.canGoBack())
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}