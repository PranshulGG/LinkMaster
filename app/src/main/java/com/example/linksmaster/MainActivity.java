package com.example.linksmaster;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private WebView webview;

    private boolean doubleBackToExitPressedOnce = false;

    private static final int REQUEST_PERMISSION_CODE = 1;

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        }
    }


    //    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        );


        setContentView(R.layout.activity_main);


        webview = findViewById(R.id.webView);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowContentAccess(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
        webview.setWebViewClient(new WebViewClientDemo());
        AndroidInterface androidInterface = new AndroidInterface(this);
        webview.addJavascriptInterface(androidInterface, "AndroidInterface");
        webview.addJavascriptInterface(new ShowToastInterface(this), "ToastAndroidShow");

        webview.setBackgroundColor(getResources().getColor(R.color.black));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webSettings.setSafeBrowsingEnabled(true);
        }

        webview.loadUrl("file:///android_asset/index.html");


        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Input error")
                        .setMessage(message)
                        .setPositiveButton("OK", (DialogInterface dialog, int which) -> result.confirm())
                        .setOnDismissListener((DialogInterface dialog) -> result.confirm())
                        .create()
                        .show();
                return true;
            }


            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Confirm delete")
                        .setMessage(message)
                        .setPositiveButton("OK", (DialogInterface dialog, int which) -> result.confirm())
                        .setNegativeButton("Cancel", (DialogInterface dialog, int which) -> result.cancel())
                        .setOnDismissListener((DialogInterface dialog) -> result.cancel())
                        .create()
                        .show();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                final EditText input = new EditText(view.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(defaultValue);
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Confirm Delete")
                        .setMessage(message)
                        .setView(input)
                        .setPositiveButton("OK", (DialogInterface dialog, int which) -> result.confirm(input.getText().toString()))
                        .setNegativeButton("Cancel", (DialogInterface dialog, int which) -> result.cancel())
                        .setOnDismissListener((DialogInterface dialog) -> result.cancel())
                        .create()
                        .show();
                return true;
            }


        });

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
            }
        }
    }




    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            webview.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callJavaScriptFunctionShared(sharedText);
                }
            }, 1000);
        }

    }

    private void callJavaScriptFunctionShared(String text) {
        String escapedText = text.replace("'", "\\'");

        webview.evaluateJavascript("javascript:receiveSharedText('" + escapedText + "')", null);
    }

    private void checkBiometricSupport() {
        BiometricManager biometricManager = BiometricManager.from(this);

        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:

                break;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                callJavaScriptFunction();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:

                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:

                break;
        }
    }

    private void showBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                if (errorCode == BiometricPrompt.ERROR_USER_CANCELED || errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    finish();
                }

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                callJavaScriptFunction();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Authenticate using your biometric credentials")
                .setConfirmationRequired(false)
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void callJavaScriptFunction() {

        webview.evaluateJavascript("javascript:removeCover();", null);
    }

    public class ShowToastInterface {
        private final Context mContext;

        public ShowToastInterface(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public void ShowToast(final String text, final String time) {
            int duration = Toast.LENGTH_SHORT;
            if (time.equals("long")) {
                duration = Toast.LENGTH_LONG;
            } else if (time.equals("short")) {
                duration = Toast.LENGTH_SHORT;
            }
            Toast.makeText(mContext, text, duration).show();
        }
    }


    public class AndroidInterface {
        private MainActivity mActivity;

        AndroidInterface(MainActivity activity) {
            mActivity = activity;
        }

        @JavascriptInterface
        public void updateStatusBarColor(final String color) {
            mActivity.runOnUiThread(new Runnable() {
                @SuppressLint("ResourceType")
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    int statusBarColor;
                    int navigationBarColor;
                    int systemUiVisibilityFlags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;

                    if (color.equals("BlueTabsDark")) {
                        statusBarColor = 0xFF0f1419;
                        navigationBarColor = 0xFF1c2026;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.blue_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.blue_dark));

                    } else if (color.equals("BlueTabsLight")) {
                        statusBarColor = 0xFFf8f9ff;
                        navigationBarColor = 0xFFebeef6;
                        setTheme(R.style.blue_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.blue_light));


                    } else if (color.equals("PurpleTabsDark")) {
                        statusBarColor = 0xFF15121a;
                        navigationBarColor = 0xFF211e26;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.purple_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.purple_dark));

                    } else if (color.equals("PurpleTabsLight")) {
                        statusBarColor = 0xFFfef7ff;
                        navigationBarColor = 0xFFf3ebf7;
                        setTheme(R.style.purple_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.purple_light));

                    } else if (color.equals("YellowTabsDark")) {
                        statusBarColor = 0xFF151409;
                        navigationBarColor = 0xFF222015;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.yellow_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.yellow_dark));

                    } else if (color.equals("YellowTabsLight")) {
                        statusBarColor = 0xFFfff9e8;
                        navigationBarColor = 0xFFf3eedc;
                        setTheme(R.style.yellow_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.yellow_light));

                    } else if (color.equals("GreenTabsDark")) {
                        statusBarColor = 0xFF0f150e;
                        navigationBarColor = 0xFF1b211a;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.green_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.green_dark));

                    } else if (color.equals("GreenTabsLight")) {
                        statusBarColor = 0xFFf5fbef;
                        navigationBarColor = 0xFFeaf0e4;
                        setTheme(R.style.green_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.green_light));

                    } else if (color.equals("RedTabsDark")) {
                        statusBarColor = 0xFF1e0f0d;
                        navigationBarColor = 0xFF2c1b19;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.red_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.red_dark));

                    } else if (color.equals("RedTabsLight")) {
                        statusBarColor = 0xFFfff8f6;
                        navigationBarColor = 0xFFffe9e4;
                        setTheme(R.style.red_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.red_light));

                    } else if (color.equals("PinkTabsDark")) {
                        statusBarColor = 0xFF181213;
                        navigationBarColor = 0xFF241e1f;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.pink_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.pink_dark));

                    } else if (color.equals("PinkTabsLight")) {
                        statusBarColor = 0xFFfff8f8;
                        navigationBarColor = 0xFFf8ebec;
                        setTheme(R.style.pink_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.pink_light));

                    } else if (color.equals("OrangeTabsDark")) {
                        statusBarColor = 0xFF17130d;
                        navigationBarColor = 0xFF241f19;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.orange_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.orange_dark));

                    } else if (color.equals("OrangeTabsLight")) {
                        statusBarColor = 0xFFfff8f4;
                        navigationBarColor = 0xFFf8ece2;
                        setTheme(R.style.orange_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.orange_light));

                    } else if (color.equals("CharcoalTabsDark")) {
                        statusBarColor = 0xFF131313;
                        navigationBarColor = 0xFF1f1f1f;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.charcol_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.charcoal_dark));

                    } else if (color.equals("CharcoalTabsLight")) {
                        statusBarColor = 0xFFf9f9f9;
                        navigationBarColor = 0xFFeeeeee;
                        setTheme(R.style.charcol_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.charcoal_light));


                    } else if (color.equals("PineGreenTabsDark")) {
                        statusBarColor = 0xFF0f1413;
                        navigationBarColor = 0xFF1b211f;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.pink_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.purple_dark));


                    } else if (color.equals("PineGreenTabsLight")) {
                        statusBarColor = 0xFFf5fbf8;
                        navigationBarColor = 0xFFeaefec;
                        setTheme(R.style.pink_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.pine_green_light));
//---------------------------------------------------------------------------------------------------

                    } else if (color.equals("BlueDark")) {
                        statusBarColor = 0xFF0f1419;
                        navigationBarColor = 0xFF0f1419;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.blue_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.blue_dark));

                    } else if (color.equals("BlueLight")) {
                        statusBarColor = 0xFFf8f9ff;
                        navigationBarColor = 0xFFf8f9ff;
                        setTheme(R.style.blue_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.blue_light));
                    } else if (color.equals("PurpleDark")) {
                        statusBarColor = 0xFF15121a;
                        navigationBarColor = 0xFF15121a;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.purple_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.purple_dark));
                    } else if (color.equals("PurpleLight")) {
                        statusBarColor = 0xFFfef7ff;
                        navigationBarColor = 0xFFfef7ff;
                        setTheme(R.style.purple_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.purple_light));
                    } else if (color.equals("YellowDark")) {
                        statusBarColor = 0xFF151409;
                        navigationBarColor = 0xFF151409;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.yellow_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.yellow_dark));
                    } else if (color.equals("YellowLight")) {
                        statusBarColor = 0xFFfff9e8;
                        navigationBarColor = 0xFFfff9e8;
                        setTheme(R.style.yellow_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.yellow_light));
                    } else if (color.equals("GreenDark")) {
                        statusBarColor = 0xFF0f150e;
                        navigationBarColor = 0xFF0f150e;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.green_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.green_dark));
                    } else if (color.equals("GreenLight")) {
                        statusBarColor = 0xFFf5fbef;
                        navigationBarColor = 0xFFf5fbef;
                        setTheme(R.style.green_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.green_light));
                    } else if (color.equals("RedDark")) {
                        statusBarColor = 0xFF1e0f0d;
                        navigationBarColor = 0xFF1e0f0d;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.red_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.red_dark));
                    } else if (color.equals("RedLight")) {
                        statusBarColor = 0xFFfff8f6;
                        navigationBarColor = 0xFFfff8f6;
                        setTheme(R.style.red_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.red_light));
                    } else if (color.equals("PinkDark")) {
                        statusBarColor = 0xFF181213;
                        navigationBarColor = 0xFF181213;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.purple_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.pink_dark));
                    } else if (color.equals("PinkLight")) {
                        statusBarColor = 0xFFfff8f8;
                        navigationBarColor = 0xFFfff8f8;
                        setTheme(R.style.pink_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.pink_light));
                    } else if (color.equals("OrangeDark")) {
                        statusBarColor = 0xFF17130d;
                        navigationBarColor = 0xFF17130d;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.orange_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.orange_dark));
                    } else if (color.equals("OrangeLight")) {
                        statusBarColor = 0xFFfff8f4;
                        navigationBarColor = 0xFFfff8f4;
                        setTheme(R.style.orange_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.orange_light));
                    } else if (color.equals("CharcoalDark")) {
                        statusBarColor = 0xFF131313;
                        navigationBarColor = 0xFF131313;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.charcol_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.charcoal_dark));
                    } else if (color.equals("CharcoalLight")) {
                        statusBarColor = 0xFFf9f9f9;
                        navigationBarColor = 0xFFf9f9f9;
                        setTheme(R.style.charcol_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.charcoal_light));
                    } else if (color.equals("PineGreenDark")) {
                        statusBarColor = 0xFF0f1413;
                        navigationBarColor = 0xFF0f1413;
                        systemUiVisibilityFlags = 0;
                        setTheme(R.style.pine_green_dark_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.pine_green_dark));
                    } else if (color.equals("PineGreenLight")) {
                        statusBarColor = 0xFFf5fbf8;
                        navigationBarColor = 0xFFf5fbf8;
                        setTheme(R.style.pine_green_light_scheme);
                        webview.setBackgroundColor(getResources().getColor(R.color.pine_green_light));

//--------------------------------------------------------------------------------------------------------------------

                    } else if (color.equals("BlueDarkDialogTabs")) {
                        statusBarColor = 0xFF06080a;
                        navigationBarColor = 0xFF0b0d0f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("BlueLightDialogTabs")) {
                        statusBarColor = 0xFF636466;
                        navigationBarColor = 0xFF5e5f62;
                        systemUiVisibilityFlags = 0;

                    } else if (color.equals("PurpleDarkDialogTabs")) {
                        statusBarColor = 0xFF08070a;
                        navigationBarColor = 0xFF0d0c0f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleLightDialogTabs")) {
                        statusBarColor = 0xFF666366;
                        navigationBarColor = 0xFF615e63;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowDarkDialogTabs")) {
                        statusBarColor = 0xFF080804;
                        navigationBarColor = 0xFF0e0d08;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowLightDialogTabs")) {
                        statusBarColor = 0xFF66645d;
                        navigationBarColor = 0xFF615f58;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenDarkDialogTabs")) {
                        statusBarColor = 0xFF060806;
                        navigationBarColor = 0xFF0b0d0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenLightDialogTabs")) {
                        statusBarColor = 0xFF626460;
                        navigationBarColor = 0xFF5e605b;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedDarkDialogTabs")) {
                        statusBarColor = 0xFF0c0605;
                        navigationBarColor = 0xFF120b0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedLightDialogTabs")) {
                        statusBarColor = 0xFF666362;
                        navigationBarColor = 0xFF665d5b;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkDarkDialogTabs")) {
                        statusBarColor = 0xFF0a0708;
                        navigationBarColor = 0xFF0e0c0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkLightDialogTabs")) {
                        statusBarColor = 0xFF666363;
                        navigationBarColor = 0xFF635e5e;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeDarkDialogTabs")) {
                        statusBarColor = 0xFF090805;
                        navigationBarColor = 0xFF0e0c0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeLightDialogTabs")) {
                        statusBarColor = 0xFF666362;
                        navigationBarColor = 0xFF635e5a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalDarkDialogTabs")) {
                        statusBarColor = 0xFF080808;
                        navigationBarColor = 0xFF0c0c0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalLightDialogTabs")) {
                        statusBarColor = 0xFF646464;
                        navigationBarColor = 0xFF5f5f5f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenDarkDialogTabs")) {
                        statusBarColor = 0xFF060808;
                        navigationBarColor = 0xFF0b0d0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenLightDialogTabs")) {
                        statusBarColor = 0xFF626463;
                        navigationBarColor = 0xFF5e605e;
                        systemUiVisibilityFlags = 0;

//--------------------------------------------------------------------------------------------------------------------

                    } else if (color.equals("BlueTopBarDark")) {
                        navigationBarColor = 0xFF0f1419;
                        statusBarColor = 0xFF1c2026;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("BlueTopBarLight")) {
                        navigationBarColor = 0xFFf8f9ff;
                        statusBarColor = 0xFFebeef6;
                    } else if (color.equals("PurpleTopBarDark")) {
                        navigationBarColor = 0xFF15121a;
                        statusBarColor = 0xFF211e26;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleTopBarLight")) {
                        navigationBarColor = 0xFFfef7ff;
                        statusBarColor = 0xFFf3ebf7;
                    } else if (color.equals("YellowTopBarDark")) {
                        navigationBarColor = 0xFF151409;
                        statusBarColor = 0xFF222015;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowTopBarLight")) {
                        navigationBarColor = 0xFFfff9e8;
                        statusBarColor = 0xFFf3eedc;
                    } else if (color.equals("GreenTopBarDark")) {
                        navigationBarColor = 0xFF0f150e;
                        statusBarColor = 0xFF1b211a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenTopBarLight")) {
                        navigationBarColor = 0xFFf5fbef;
                        statusBarColor = 0xFFeaf0e4;
                    } else if (color.equals("RedTopBarDark")) {
                        navigationBarColor = 0xFF1e0f0d;
                        statusBarColor = 0xFF2c1b19;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedTopBarLight")) {
                        navigationBarColor = 0xFFfff8f6;
                        statusBarColor = 0xFFffe9e4;
                    } else if (color.equals("PinkTopBarDark")) {
                        navigationBarColor = 0xFF181213;
                        statusBarColor = 0xFF241e1f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkTopBarLight")) {
                        navigationBarColor = 0xFFfff8f8;
                        statusBarColor = 0xFFf8ebec;
                    } else if (color.equals("OrangeTopBarDark")) {
                        navigationBarColor = 0xFF17130d;
                        statusBarColor = 0xFF241f19;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeTopBarLight")) {
                        navigationBarColor = 0xFFfff8f4;
                        statusBarColor = 0xFFf8ece2;
                    } else if (color.equals("CharcoalTopBarDark")) {
                        navigationBarColor = 0xFF131313;
                        statusBarColor = 0xFF1f1f1f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalTopBarLight")) {
                        navigationBarColor = 0xFFf9f9f9;
                        statusBarColor = 0xFFeeeeee;
                    } else if (color.equals("PineGreenTopBarDark")) {
                        navigationBarColor = 0xFF0f1413;
                        statusBarColor = 0xFF1b211f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenTopBarLight")) {
                        navigationBarColor = 0xFFf5fbf8;
                        statusBarColor = 0xFFeaefec;

//------------------------------------------------------------------------------------------------------

                    } else if (color.equals("BlueDarkDialogRegular")) {
                        statusBarColor = 0xFF06080a;
                        navigationBarColor = 0xFF06080a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("BlueLightDialogRegular")) {
                        statusBarColor = 0xFF636466;
                        navigationBarColor = 0xFF636466;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleDarkDialogRegular")) {
                        statusBarColor = 0xFF08070a;
                        navigationBarColor = 0xFF08070a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleLightDialogRegular")) {
                        statusBarColor = 0xFF666366;
                        navigationBarColor = 0xFF666366;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowDarkDialogRegular")) {
                        statusBarColor = 0xFF080804;
                        navigationBarColor = 0xFF080804;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowLightDialogRegular")) {
                        statusBarColor = 0xFF66645d;
                        navigationBarColor = 0xFF66645d;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenDarkDialogRegular")) {
                        statusBarColor = 0xFF060806;
                        navigationBarColor = 0xFF060806;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenLightDialogRegular")) {
                        statusBarColor = 0xFF626460;
                        navigationBarColor = 0xFF626460;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedDarkDialogRegular")) {
                        statusBarColor = 0xFF0c0605;
                        navigationBarColor = 0xFF0c0605;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedLightDialogRegular")) {
                        statusBarColor = 0xFF666362;
                        navigationBarColor = 0xFF666362;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkDarkDialogRegular")) {
                        statusBarColor = 0xFF0a0708;
                        navigationBarColor = 0xFF0a0708;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkLightDialogRegular")) {
                        statusBarColor = 0xFF666363;
                        navigationBarColor = 0xFF666363;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeDarkDialogRegular")) {
                        statusBarColor = 0xFF090805;
                        navigationBarColor = 0xFF090805;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeLightDialogRegular")) {
                        statusBarColor = 0xFF666362;
                        navigationBarColor = 0xFF666362;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalDarkDialogRegular")) {
                        statusBarColor = 0xFF080808;
                        navigationBarColor = 0xFF080808;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalLightDialogRegular")) {
                        statusBarColor = 0xFF646464;
                        navigationBarColor = 0xFF646464;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenDarkDialogRegular")) {
                        statusBarColor = 0xFF060808;
                        navigationBarColor = 0xFF060808;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenLightDialogRegular")) {
                        statusBarColor = 0xFF626463;
                        navigationBarColor = 0xFF626463;
                        systemUiVisibilityFlags = 0;

                        //---------------------------------------------------------------------------------------------------------
                    } else if (color.equals("BlueBothBarDark")) {
                        navigationBarColor = 0xFF1c2026;
                        statusBarColor = 0xFF1c2026;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("BlueBothBarLight")) {
                        navigationBarColor = 0xFFebeef6;
                        statusBarColor = 0xFFebeef6;
                    } else if (color.equals("PurpleBothBarDark")) {
                        navigationBarColor = 0xFF211e26;
                        statusBarColor = 0xFF211e26;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleBothBarLight")) {
                        navigationBarColor = 0xFFf3ebf7;
                        statusBarColor = 0xFFf3ebf7;
                    } else if (color.equals("YellowBothBarDark")) {
                        navigationBarColor = 0xFF222015;
                        statusBarColor = 0xFF222015;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowBothBarLight")) {
                        navigationBarColor = 0xFFf3eedc;
                        statusBarColor = 0xFFf3eedc;
                    } else if (color.equals("GreenBothBarDark")) {
                        navigationBarColor = 0xFF1b211a;
                        statusBarColor = 0xFF1b211a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenBothBarLight")) {
                        navigationBarColor = 0xFFeaf0e4;
                        statusBarColor = 0xFFeaf0e4;
                    } else if (color.equals("RedBothBarDark")) {
                        navigationBarColor = 0xFF2c1b19;
                        statusBarColor = 0xFF2c1b19;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedBothBarLight")) {
                        navigationBarColor = 0xFFffe9e4;
                        statusBarColor = 0xFFffe9e4;
                    } else if (color.equals("PinkBothBarDark")) {
                        navigationBarColor = 0xFF241e1f;
                        statusBarColor = 0xFF241e1f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkBothBarLight")) {
                        navigationBarColor = 0xFFf8ebec;
                        statusBarColor = 0xFFf8ebec;
                    } else if (color.equals("OrangeBothBarDark")) {
                        navigationBarColor = 0xFF241f19;
                        statusBarColor = 0xFF241f19;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeBothBarLight")) {
                        navigationBarColor = 0xFFf8ece2;
                        statusBarColor = 0xFFf8ece2;
                    } else if (color.equals("CharcoalBothBarDark")) {
                        navigationBarColor = 0xFF1f1f1f;
                        statusBarColor = 0xFF1f1f1f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalBothBarLight")) {
                        navigationBarColor = 0xFFeeeeee;
                        statusBarColor = 0xFFeeeeee;
                    } else if (color.equals("PineGreenBothBarDark")) {
                        navigationBarColor = 0xFF1b211f;
                        statusBarColor = 0xFF1b211f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenBothBarLight")) {
                        navigationBarColor = 0xFFeaefec;
                        statusBarColor = 0xFFeaefec;

//----------------------------------------------------------------------------------------------------------------------------------

                    } else if (color.equals("BlueDarkDialogBothBar")) {
                        statusBarColor = 0xFF0b0d0f;
                        navigationBarColor = 0xFF0b0d0f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("BlueLightDialogBothBar")) {
                        statusBarColor = 0xFF5e5f62;
                        navigationBarColor = 0xFF5e5f62;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleDarkDialogBothBar")) {
                        statusBarColor = 0xFF0d0c0f;
                        navigationBarColor = 0xFF0d0c0f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleLightDialogBothBar")) {
                        statusBarColor = 0xFF615e63;
                        navigationBarColor = 0xFF615e63;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowDarkDialogBothBar")) {
                        statusBarColor = 0xFF0e0d08;
                        navigationBarColor = 0xFF0e0d08;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowLightDialogBothBar")) {
                        statusBarColor = 0xFF615f58;
                        navigationBarColor = 0xFF615f58;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenDarkDialogBothBar")) {
                        statusBarColor = 0xFF0b0d0a;
                        navigationBarColor = 0xFF0b0d0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenLightDialogBothBar")) {
                        statusBarColor = 0xFF5e605b;
                        navigationBarColor = 0xFF5e605b;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedDarkDialogBothBar")) {
                        statusBarColor = 0xFF120b0a;
                        navigationBarColor = 0xFF120b0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedLightDialogBothBar")) {
                        statusBarColor = 0xFF665d5b;
                        navigationBarColor = 0xFF665d5b;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkDarkDialogBothBar")) {
                        statusBarColor = 0xFF0e0c0c;
                        navigationBarColor = 0xFF0e0c0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkLightDialogBothBar")) {
                        statusBarColor = 0xFF635e5e;
                        navigationBarColor = 0xFF635e5e;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeDarkDialogBothBar")) {
                        statusBarColor = 0xFF0e0c0a;
                        navigationBarColor = 0xFF0e0c0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeLightDialogBothBar")) {
                        statusBarColor = 0xFF635e5a;
                        navigationBarColor = 0xFF635e5a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalDarkDialogBothBar")) {
                        statusBarColor = 0xFF0c0c0c;
                        navigationBarColor = 0xFF0c0c0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalLightDialogBothBar")) {
                        statusBarColor = 0xFF5f5f5f;
                        navigationBarColor = 0xFF5f5f5f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenDarkDialogBothBar")) {
                        statusBarColor = 0xFF0b0d0c;
                        navigationBarColor = 0xFF0b0d0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenLightDialogBothBar")) {
                        statusBarColor = 0xFF5e605e;
                        navigationBarColor = 0xFF5e605e;
                        systemUiVisibilityFlags = 0;

//-------------------------------------------------------------------------------------------------------------------

                    } else if (color.equals("BlueDarkDialogTopBar")) {
                        navigationBarColor = 0xFF06080a;
                        statusBarColor = 0xFF0b0d0f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("BlueLightDialogTopBar")) {
                        navigationBarColor = 0xFF636466;
                        statusBarColor = 0xFF5e5f62;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleDarkDialogTopBar")) {
                        navigationBarColor = 0xFF08070a;
                        statusBarColor = 0xFF0d0c0f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleLightDialogTopBar")) {
                        navigationBarColor = 0xFF666366;
                        statusBarColor = 0xFF615e63;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowDarkDialogTopBar")) {
                        navigationBarColor = 0xFF080804;
                        statusBarColor = 0xFF0e0d08;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowLightDialogTopBar")) {
                        navigationBarColor = 0xFF66645d;
                        statusBarColor = 0xFF615f58;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenDarkDialogTopBar")) {
                        navigationBarColor = 0xFF060806;
                        statusBarColor = 0xFF0b0d0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenLightDialogTopBar")) {
                        navigationBarColor = 0xFF626460;
                        statusBarColor = 0xFF5e605b;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedDarkDialogTopBar")) {
                        navigationBarColor = 0xFF0c0605;
                        statusBarColor = 0xFF120b0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedLightDialogTopBar")) {
                        navigationBarColor = 0xFF666362;
                        statusBarColor = 0xFF665d5b;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkDarkDialogTopBar")) {
                        navigationBarColor = 0xFF0a0708;
                        statusBarColor = 0xFF0e0c0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkLightDialogTopBar")) {
                        navigationBarColor = 0xFF666363;
                        statusBarColor = 0xFF635e5e;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeDarkDialogTopBar")) {
                        navigationBarColor = 0xFF090805;
                        statusBarColor = 0xFF0e0c0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeLightDialogTopBar")) {
                        navigationBarColor = 0xFF666362;
                        statusBarColor = 0xFF635e5a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalDarkDialogTopBar")) {
                        navigationBarColor = 0xFF080808;
                        statusBarColor = 0xFF0c0c0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalLightDialogTopBar")) {
                        navigationBarColor = 0xFF646464;
                        statusBarColor = 0xFF5f5f5f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenDarkDialogTopBar")) {
                        navigationBarColor = 0xFF060808;
                        statusBarColor = 0xFF0b0d0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenLightDialogTopBar")) {
                        navigationBarColor = 0xFF626463;
                        statusBarColor = 0xFF5e605e;
                        systemUiVisibilityFlags = 0;


// -----------------------------------------------------------------------------------------------------

                    } else if (color.equals("BlueDarkSheetTopBar")) {
                        navigationBarColor = 0xFF181c22;
                        statusBarColor = 0xFF0b0d0f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("BlueLightSheetTopBar")) {
                        navigationBarColor = 0xFFf1f3fc;
                        statusBarColor = 0xFF5e5f62;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleDarkSheetTopBar")) {
                        navigationBarColor = 0xFF1d1a22;
                        statusBarColor = 0xFF0d0c0f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleLightSheetTopBar")) {
                        navigationBarColor = 0xFFf9f1fc;
                        statusBarColor = 0xFF615e63;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowDarkSheetTopBar")) {
                        navigationBarColor = 0xFF1d1c11;
                        statusBarColor = 0xFF0e0d08;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowLightSheetTopBar")) {
                        navigationBarColor = 0xFFf9f3e1;
                        statusBarColor = 0xFF615f58;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenDarkSheetTopBar")) {
                        navigationBarColor = 0xFF171d16;
                        statusBarColor = 0xFF0b0d0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenLightSheetTopBar")) {
                        navigationBarColor = 0xFFf0f6ea;
                        statusBarColor = 0xFF5e605b;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedDarkSheetTopBar")) {
                        navigationBarColor = 0xFF281715;
                        statusBarColor = 0xFF120b0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedLightSheetTopBar")) {
                        navigationBarColor = 0xFFfff1ed;
                        statusBarColor = 0xFF665d5b;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkDarkSheetTopBar")) {
                        navigationBarColor = 0xFF201a1b;
                        statusBarColor = 0xFF0e0c0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkLightSheetTopBar")) {
                        navigationBarColor = 0xFFfef0f2;
                        statusBarColor = 0xFF635e5e;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeDarkSheetTopBar")) {
                        navigationBarColor = 0xFF201b15;
                        statusBarColor = 0xFF0e0c0a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeLightSheetTopBar")) {
                        navigationBarColor = 0xFFfef2e8;
                        statusBarColor = 0xFF635e5a;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalDarkSheetTopBar")) {
                        navigationBarColor = 0xFF1b1b1b;
                        statusBarColor = 0xFF0c0c0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalLightSheetTopBar")) {
                        navigationBarColor = 0xFFf3f3f3;
                        statusBarColor = 0xFF5f5f5f;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenDarkSheetTopBar")) {
                        navigationBarColor = 0xFF171d1b;
                        statusBarColor = 0xFF0b0d0c;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenLightSheetTopBar")) {
                        navigationBarColor = 0xFFeff5f2;
                        statusBarColor = 0xFF5e605e;
                        systemUiVisibilityFlags = 0;
//-----------------------------------------------------------------------------------------------------

                    } else if (color.equals("BlueDarkSheetRegular")) {
                        statusBarColor = 0xFF06080a;
                        navigationBarColor = 0xFF181c22;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("BlueLightSheetRegular")) {
                        statusBarColor = 0xFF636466;
                        navigationBarColor = 0xFFf1f3fc;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleDarkSheetRegular")) {
                        statusBarColor = 0xFF08070a;
                        navigationBarColor = 0xFF1d1a22;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PurpleLightSheetRegular")) {
                        statusBarColor = 0xFF666366;
                        navigationBarColor = 0xFFf9f1fc;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowDarkSheetRegular")) {
                        statusBarColor = 0xFF080804;
                        navigationBarColor = 0xFF1d1c11;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("YellowLightSheetRegular")) {
                        statusBarColor = 0xFF66645d;
                        navigationBarColor = 0xFFf9f3e1;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenDarkSheetRegular")) {
                        statusBarColor = 0xFF060806;
                        navigationBarColor = 0xFF171d16;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("GreenLightSheetRegular")) {
                        statusBarColor = 0xFF626460;
                        navigationBarColor = 0xFFf0f6ea;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedDarkSheetRegular")) {
                        statusBarColor = 0xFF0c0605;
                        navigationBarColor = 0xFF281715;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("RedLightSheetRegular")) {
                        statusBarColor = 0xFF666362;
                        navigationBarColor = 0xFFfff1ed;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkDarkSheetRegular")) {
                        statusBarColor = 0xFF0a0708;
                        navigationBarColor = 0xFF201a1b;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PinkLightSheetRegular")) {
                        statusBarColor = 0xFF666363;
                        navigationBarColor = 0xFFfef0f2;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeDarkSheetRegular")) {
                        statusBarColor = 0xFF090805;
                        navigationBarColor = 0xFF201b15;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("OrangeLightSheetRegular")) {
                        statusBarColor = 0xFF666362;
                        navigationBarColor = 0xFFfef2e8;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalDarkSheetRegular")) {
                        statusBarColor = 0xFF080808;
                        navigationBarColor = 0xFF1b1b1b;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("CharcoalLightSheetRegular")) {
                        statusBarColor = 0xFF646464;
                        navigationBarColor = 0xFFf3f3f3;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenDarkSheetRegular")) {
                        statusBarColor = 0xFF060808;
                        navigationBarColor = 0xFF171d1b;
                        systemUiVisibilityFlags = 0;
                    } else if (color.equals("PineGreenLightSheetRegular")) {
                        statusBarColor = 0xFF626463;
                        navigationBarColor = 0xFFeff5f2;
                        systemUiVisibilityFlags = 0;


                    } else if (color.equals("keepiton")) {
                        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        return;
                    } else if (color.equals("keepitoff")) {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        return;
                    } else if (color.equals("itsOn")) {
                        Toast.makeText(mActivity, "Your device will stay awake", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (color.equals("ItsOff")) {
                        Toast.makeText(mActivity, "Your device will go to sleep at the default time", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (color.equals("ShowBiometric")) {
                        checkBiometricSupport();
                        showBiometricPrompt();
                        return;

                    } else {
                        Toast.makeText(mActivity, "not found", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    int currentStatusBarColor = mActivity.getWindow().getStatusBarColor();
                    int currentNavigationBarColor = mActivity.getWindow().getNavigationBarColor();

                    ObjectAnimator statusBarAnimator = ObjectAnimator.ofObject(
                            mActivity.getWindow(),
                            "statusBarColor",
                            new ArgbEvaluator(),
                            currentStatusBarColor,
                            statusBarColor
                    );

                    statusBarAnimator.setDuration(200);
                    statusBarAnimator.start();

                    ObjectAnimator navBarAnimator = ObjectAnimator.ofObject(
                            mActivity.getWindow(),
                            "navigationBarColor",
                            new ArgbEvaluator(),
                            currentNavigationBarColor,
                            navigationBarColor
                    );

                    navBarAnimator.setDuration(200);
                    navBarAnimator.start();

                    mActivity.getWindow().setNavigationBarColor(navigationBarColor);

                    View decorView = mActivity.getWindow().getDecorView();
                    decorView.setSystemUiVisibility(systemUiVisibilityFlags);


                }
            });
        }

        public void back() {
            onBackPressed();
        }

        private void clearClipboard() {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                clipboard.setPrimaryClip(ClipData.newPlainText("", ""));
            }
        }

    }


    private static class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (isValidUrl(url)) {
                if (shouldOpenInBrowser(url)) {
                    // Open in an external browser if the URL is valid and should not be loaded inside WebView
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true; // Prevent WebView from loading it
                } else {
                    // Load inside the WebView for internal/excluded URLs
                    view.loadUrl(url);
                    return false; // Let WebView handle the URL
                }
            } else {
                // Show toast if the link is invalid and do nothing
                if (!isExcludedUrl(url)) {
                    Context context = view.getContext();
                    Toast.makeText(context, "Invalid link", Toast.LENGTH_SHORT).show();
                }
                return true; // Prevent WebView from loading the invalid URL
            }
        }

        private boolean isValidUrl(String url) {
            // Check if the URL is valid (starts with "http", "mailto", or it's an excluded URL)
            return url != null && (url.startsWith("http") || url.startsWith("mailto:") || isExcludedUrl(url));
        }

        private boolean shouldOpenInBrowser(String url) {
            // URLs that should open within the WebView (excluded URLs)
            return !isExcludedUrl(url);
        }

        private boolean isExcludedUrl(String url) {
            // URLs that should not be considered invalid and should load inside the WebView
            return url.startsWith("file:///android_asset/index.html") ||
                    url.startsWith("file:///android_asset/pages/settings.html") ||
                    url.startsWith("file:///android_asset/pages/about.html") ||
                    url.startsWith("file:///android_asset/pages/aboutPages/licenses.html") ||
                    url.startsWith("file:///android_asset/pages/aboutPages/PrivacyPolicy.html") ||
                    url.startsWith("file:///android_asset/pages/aboutPages/TermsConditions.html");
        }
    }

}