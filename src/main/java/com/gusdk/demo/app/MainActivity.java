package com.gusdk.demo.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aly.sdk.ALYAnalysis;
import com.applovin.sdk.AppLovinSdk;
import com.gu.sdk.GuBannerAd;
import com.gu.sdk.GuInterstitialAd;
import com.gu.sdk.GuRewardVideoAd;
import com.gu.sdk.GuSDK;
import com.gu.sdk.listener.GuAdsBannerListener;
import com.gu.sdk.listener.GuAdsLoadListener;
import com.gu.sdk.listener.GuAdsShowListener;
import com.gu.sdk.listener.GuInitListener;
import com.gu.sdk.manager.GuPrivacyManager;
import com.ironsource.mediationsdk.integration.IntegrationHelper;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.REQUEST_INSTALL_PACKAGES;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends BaseActivity {
    private static String TAG = "roy_gusdk";
    GuInterstitialAd guInterstitialAd;
    GuRewardVideoAd rewardVideoAd;
    GuBannerAd bannerAd;
    FrameLayout banner_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //存储权限
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_PHONE_STATE, READ_CONTACTS, ACCESS_FINE_LOCATION, REQUEST_INSTALL_PACKAGES}, 001);
        }
        ALYAnalysis.enalbeDebugMode(true);
        ALYAnalysis.init(getApplicationContext(), "600226", "32408");
//        IntegrationHelper.validateIntegration(this);
//        AppLovinSdk.getInstance(this).getSettings().setVerboseLogging(true);
        GuSDK.setAccessPrivacyInfoStatus(this, GuPrivacyManager.AccessPrivacyNameEnum.GDPR, GuPrivacyManager.AccessPrivacyInfoStatusEnum.AccessPrivacyInfoStatusAccepted);
        GuSDK.setAccessPrivacyInfoStatus(this, GuPrivacyManager.AccessPrivacyNameEnum.CPPA, GuPrivacyManager.AccessPrivacyInfoStatusEnum.AccessPrivacyInfoStatusAccepted);
        GuSDK.setAge(this, 18);

        GuSDK.initSDK(this, new GuInitListener() {
            @Override
            public void onInitSuccess() {
                Log.i(TAG, "onInitSuccess: ");
                initInterAds();
                initRwdAds();
                initBannerAds();
            }

            @Override
            public void onInitFail(String s) {
                Log.i(TAG, "onInitFail: " + s);
            }
        });
    }

    private void initBannerAds() {
        bannerAd = new GuBannerAd(this, "sample_banner");
        banner_container = findViewById(R.id.bannerContainer);
        bannerAd.setBannerListener(new GuAdsBannerListener() {
            @Override
            public void onLoaded() {
                Log.i(TAG, "banner onLoaded: ");
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                banner_container.addView(bannerAd.getBannerView(), 0, layoutParams);
            }


            @Override
            public void onError() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        banner_container.removeAllViews();
                    }
                });
            }

            @Override
            public void onClick() {

            }
        });
    }

    private GuAdsBannerListener createListener() {
        return new GuAdsBannerListener() {
            @Override
            public void onLoaded() {
                Log.i(TAG, "banner onLoaded: ");
                banner_container.addView(bannerAd.getBannerView());
                bannerAd.setBannerListener(createListener());
            }

            @Override
            public void onError() {
                Log.i(TAG, "banner onError: ");
            }

            @Override
            public void onClick() {
                Log.i(TAG, "banner onClick: ");
            }
        };
    }

    private void initRwdAds() {
        rewardVideoAd = GuRewardVideoAd.getInstance(this);
    }

    private void initInterAds() {
        guInterstitialAd = new GuInterstitialAd(this);
    }

    public void setCallBackToInterAds(View view) {
        guInterstitialAd.setLoadListener(new GuAdsLoadListener() {
            @Override
            public void onLoadFailed() {
                Log.i(TAG, " inter onLoadFailed: ");
            }

            @Override
            public void onLoadSuccessed() {
                Log.i(TAG, " inter onLoadSuccessed: ");
            }
        });
    }

    public void showInterAds(View view) {
        if (guInterstitialAd.isReady()) {
            guInterstitialAd.show("interads", new GuAdsShowListener() {
                @Override
                public void onAdShowFail(String s) {
                    Log.i(TAG, "onAdShowFail: " + s);
                }

                @Override
                public void onAdClicked() {
                    Log.i(TAG, "onAdClicked");
                }

                @Override
                public void onAdClosed() {
                    Log.i(TAG, "onAdClosed");
                }

                @Override
                public void onAdDisplayed() {
                    Log.i(TAG, "onAdDisplayed");
                }

                @Override
                public void onAdReward() {
                    Log.i(TAG, "onAdReward");
                }

                @Override
                public void onAdDontReward(String s) {
                    Log.i(TAG, "sonAdDontReward : " + s);
                }
            });
        } else {
            Log.i(TAG, "showInterAds: no ads");
        }
    }

    public void setCallBackToRwdAds(View view) {
        rewardVideoAd.setLoadListener(new GuAdsLoadListener() {
            @Override
            public void onLoadFailed() {
                Log.i(TAG, " RwdAds onLoadFailed: ");
            }

            @Override
            public void onLoadSuccessed() {
                Log.i(TAG, " RwdAds onLoadSuccessed: ");

            }
        });

    }

    public void showRwdAds(View view) {
        if (!rewardVideoAd.isReady()) {

            Log.i(TAG, "showRwdAds: no ads");
            return;
        }
        rewardVideoAd.show("gameCenter", new GuAdsShowListener() {
            @Override
            public void onAdShowFail(String s) {
                Log.i(TAG, "onAdShowFail: " + s);
            }

            @Override
            public void onAdClicked() {
                Log.i(TAG, "onAdClicked");
            }

            @Override
            public void onAdClosed() {
                Log.i(TAG, "onAdClosed");
            }

            @Override
            public void onAdDisplayed() {
                Log.i(TAG, "onAdDisplayed");
            }

            @Override
            public void onAdReward() {
                Log.i(TAG, "onAdReward");
            }

            @Override
            public void onAdDontReward(String s) {
                Log.i(TAG, "sonAdDontReward : " + s);
            }
        });
    }

    public void getScreenInfo(View view) {
        methodA();
        methodB();
        methodC();

    }

    private void methodC() {
        WindowManager wm = (WindowManager) (this.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;
        int mScreenHeight = dm.heightPixels;
        log3Params("methodC", mScreenWidth, mScreenHeight);
    }

    private void methodB() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        log3Params("methodB", screenWidth, screenHeight);
    }

    private void methodA() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        log3Params("methodA", screenWidth, screenHeight);
    }

    private void log3Params(String methodName, int width, int height) {
        Log.i(TAG, methodName + " : screenWidth : " + width + " screenHeight :" + height);
    }

    public void showBannenr(View view) {
        banner_container.setVisibility(View.VISIBLE);
    }

    public void hidebanner(View view) {
        banner_container.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
