package com.gusdk.demo.app;

import android.app.Activity;
import android.os.Bundle;

import com.gu.sdk.GuSDK;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GuSDK.onStart(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        GuSDK.onStop(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GuSDK.onDestroy(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GuSDK.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GuSDK.onResume(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        GuSDK.onRestart(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GuSDK.onBackPressed(this);
    }
}
