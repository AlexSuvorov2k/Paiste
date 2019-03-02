package ru.alexsuvorov.paistewiki.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ru.alexsuvorov.paistewiki.App;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class InstagramViewerFragment extends Fragment {

    AppPreferences appPreferences;
    Context context;
    private WebView webView;
    private FloatingActionButton floatingActionButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appPreferences = new AppPreferences(context);
        this.context = context;
        ((App) getActivity().getApplication()).setLocale();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_instagram, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.web_view);
        webView.setWebViewClient(new InstaWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.instagram.com/explore/tags/paistecymbals/");
        floatingActionButton = view.findViewById(R.id.back_fab);
    }

    public class InstaWebViewClient extends WebViewClient {

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        private void linkBackButton(boolean flag) {
            if (flag)
                floatingActionButton.setVisibility(View.VISIBLE);
            else
                floatingActionButton.setVisibility(View.GONE);

            floatingActionButton.setOnClickListener(view -> {
                if (webView.canGoBack())
                    webView.goBack();
            });
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (webView.canGoBack())
                linkBackButton(true);
            else
                linkBackButton(false);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}