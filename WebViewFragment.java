package com.yxjy.app.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxjy.app.R;
import com.yxjy.app.base.BaseFragment;
import com.yxjy.app.ui.MainsActivity;
import com.yxjy.app.utils.UriUtils;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.yxjy.app.ui.MainsActivity.*;
import static com.yxjy.app.ui.MainsActivity.FILECHOOSER_RESULTCODE;

public class WebViewFragment extends Fragment {

    final int FILECHOOSER_RESULTCODE = 1000;
    private WebView mWebView;
    ValueCallback<Uri> mUploadMessage;
    ValueCallback<Uri[]> mUploadMessages;

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle, null);
        mWebView = (WebView) view.findViewById(R.id.webView);
        // 设置webview的展示形式
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //设置title
            }
        });
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(this, "demo");//和js交互时的对象名字
        mWebView.loadUrl("url");//需要加载的url
        mWebView.setWebChromeClient(new XHSWebChromeClient());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (null == mUploadMessages) return;
                Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                if (result == null) {
                    mUploadMessages.onReceiveValue(null);
                    mUploadMessages = null;
                    return;
                }
                String path = UriUtils.getImageAbsolutePath(getActivity(), result);
                if (TextUtils.isEmpty(path)) {
                    mUploadMessages.onReceiveValue(null);
                    mUploadMessages = null;
                    return;
                }
                Uri uri = Uri.fromFile(new File(path));
                mUploadMessages.onReceiveValue(new Uri[]{uri});
                mUploadMessages = null;
            } else {
                if (null == mUploadMessage) return;
                Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                if (result == null) {
                    mUploadMessage.onReceiveValue(null);
                    mUploadMessage = null;
                    return;
                }
                String path = UriUtils.getImageAbsolutePath(getActivity(), result);
                if (TextUtils.isEmpty(path)) {
                    mUploadMessage.onReceiveValue(null);
                    mUploadMessage = null;
                    return;
                }
                Uri uri = Uri.fromFile(new File(path));
                mUploadMessage.onReceiveValue(uri);
                mUploadMessage = null;
            }
        }
    }

    public class XHSWebChromeClient extends WebChromeClient {
        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            String type = TextUtils.isEmpty(acceptType) ? "*/*" : acceptType;
            i.setType(type);
            startActivityForResult(Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE);
        }

        // For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            String type = TextUtils.isEmpty(acceptType) ? "*/*" : acceptType;
            i.setType(type);
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
        }

        //Android 5.0+
        @Override
        @SuppressLint("NewApi")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (mUploadMessages != null) {
                mUploadMessages.onReceiveValue(null);
            }
            mUploadMessages = filePathCallback;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null
                    && fileChooserParams.getAcceptTypes().length > 0) {
                i.setType(fileChooserParams.getAcceptTypes()[0]);
            } else {
                i.setType("*/*");
            }
            startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            return true;
        }
    }
}
