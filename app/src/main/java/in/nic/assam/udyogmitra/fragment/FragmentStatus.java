package in.nic.assam.udyogmitra.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import in.nic.assam.udyogmitra.R;


public class FragmentStatus extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    String mParam1;
    String mParam2;

    public FragmentStatus() {
        // Required empty public constructor
    }


    public static FragmentStatus newInstance(String param1, String param2) {
        FragmentStatus fragment = new FragmentStatus();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        // Find the WebView by its unique ID
        WebView w = view.findViewById(R.id.webView);

        // loading http://www.google.com url in the the WebView.


        // this will enable the javascript.
        //w.getSettings().setJavaScriptEnabled(true);


        WebSettings webSettings = w.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        //webSettings.setUseWideViewPort(true);
        //webSettings.setBuiltInZoomControls(true);
       // webSettings.setDisplayZoomControls(false);
        //webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        w.setWebChromeClient(new WebChromeClient());
        w.setWebViewClient(new WebViewClient());


       // 'chrome browser -mobile mode
        //'wvs.setUserAgentString(WebView1,"Mozilla/5.0 (Linux; Android 7.0; Redmi Note 4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.111 Mobile Safari/537.36")

        //'chrome browser -desktop mode
        webSettings.setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.111 Safari/537.36");



        w.loadUrl("file:///android_asset/test.html");

//        // WebViewClient allows you to handle
//        // onPageFinished and override Url loading.
//        w.setWebViewClient(new WebViewClient());
//
//        //w.loadUrl("http://eodb.assam.gov.in");
//        //w.loadUrl("javascript:<script>function citizenApplionWindow(){alert(\"ggg\")}citizenApplionWindow();</script>");
//        //w.loadUrl("javascript:(function citizenApplionWindow(){var t=document.createElement(\"form\");t.method=\"POST\",t.action=\"https://eservices.assam.gov.in/citizenApplication.html\";var e=document.createElement(\"input\");e.setAttribute(\"type\",\"hidden\"),e.setAttribute(\"name\",\"OWASP_CSRFTOKEN\"),e.setAttribute(\"value\",\"Z7HM-MCHZ-LFG5-PRRD-DLPA-DYA4-K8J9-DD1O\"),t.appendChild(e),t.target=\"trackAppForm\",window.open(\"\",\"trackAppForm\",\"height=500,width=1000\"),document.body.appendChild(t),t.submit()}citizenApplionWindow();)()");

//        WebView myWebView = view.findViewById(R.id.webView);
//        myWebView.getSettings().setJavaScriptEnabled(true);
//        myWebView.getSettings().setAllowFileAccessFromFileURLs(true);
//        myWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
//
//        myWebView.setWebViewClient(new WebViewClient());
//        myWebView.setWebChromeClient(new WebChromeClient() {
//            // Grant permissions for cam
//            @Override
//            public void onPermissionRequest(final PermissionRequest request) {
//                Log.d("aaaaaa", "onPermissionRequest");
//                getActivity().runOnUiThread(new Runnable() {
//                    @TargetApi(Build.VERSION_CODES.M)
//                    @Override
//                    public void run() {
//                        Log.d("aaaa", request.getOrigin().toString());
//                        if(request.getOrigin().toString().equals("file:///")) {
//                            Log.d("aaaa", "GRANTED");
//                            request.grant(request.getResources());
//                        } else {
//                            Log.d("aaaa", "DENIED");
//                            request.deny();
//                        }
//                    }
//                });
//            }
//        });
//        myWebView.loadUrl("https://eodb.assam.gov.in/");

        return view;
    }
}