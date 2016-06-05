package com.superapp.headsupvietnamesedoanchucungbanbebatquizgameshow.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.onesignal.OneSignal;
import com.samsung.android.sdk.motion.Smotion;
import com.samsung.android.sdk.motion.SmotionCall;
import com.samsung.android.sdk.motion.SmotionPedometer;
import com.superapp.headsupvietnamesedoanchucungbanbebatquizgameshow.R;
import com.superapp.headsupvietnamesedoanchucungbanbebatquizgameshow.adapter.MenuAdapter;
import com.superapp.headsupvietnamesedoanchucungbanbebatquizgameshow.application.AnalyticsApplication;
import com.superapp.headsupvietnamesedoanchucungbanbebatquizgameshow.utils.DataUtils;

import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.ChartboostDelegate;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity implements MenuAdapter.HandleClick {
    private RecyclerView _recyclerView;
    private MenuAdapter _adapter;
    private List<Integer> mList;
    private static final String TAG = "MainActivity";
    private Tracker mTracker;
    private long count;
    //Gắn cho có
    private Smotion mMotion;
    private SmotionPedometer mPedometer;
    private SmotionCall mCall;
    InterstitialAd mInterstitialAd;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        OneSignal.startInit(this).init();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        requestNewInterstitial();

        Chartboost.startWithAppId(this, "56d8630cf789821203cdc036", "3cf4ad96da07137e375b1c576f5b9d7986fb356c");
        Chartboost.setDelegate(delegate);
        Chartboost.onCreate(this);
        Chartboost.cacheRewardedVideo(CBLocation.LOCATION_ACHIEVEMENTS);
        Chartboost.setAutoCacheAds(true);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        count=0;
        mList = DataUtils.getINSTANCE().getListMenu();
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        _recyclerView = (RecyclerView) findViewById(R.id.rv_ListMenu);
        _adapter = new MenuAdapter(this, mList);
        _recyclerView.setAdapter(_adapter);
        _recyclerView.setLayoutManager(layoutManager);
        int times = DataUtils.getINSTANCE().getTimes();
        if (times == 3) {
            Log.d(TAG, "Show Ads");
            DataUtils.getINSTANCE().setTimes(0);
            if (Chartboost.hasInterstitial(CBLocation.LOCATION_GAME_SCREEN)) {
                Chartboost.showInterstitial(CBLocation.LOCATION_GAME_SCREEN);
            } else {
                Chartboost.cacheInterstitial(CBLocation.LOCATION_GAME_SCREEN);
            }
            Chartboost.showInterstitial(CBLocation.LOCATION_GAME_SCREEN);

        }
    }

    private ChartboostDelegate delegate = new ChartboostDelegate() {
        @Override
        public void didCompleteRewardedVideo(String location, int reward) {
            super.didCompleteRewardedVideo(location, reward);
            DataUtils.getINSTANCE().setKey(true);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Chartboost.onResume(this);
        Log.i(TAG, "Setting screen name: Menu");
        mTracker.setScreenName("Image~Menu");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        int times = DataUtils.getINSTANCE().getTimes();
        if (times == 3) {
            Log.d(TAG, "Show Ads");
            DataUtils.getINSTANCE().setTimes(0);
//            if (Chartboost.hasInterstitial(CBLocation.LOCATION_GAME_SCREEN)) {
//                Chartboost.showInterstitial(CBLocation.LOCATION_GAME_SCREEN);
//            } else {
//                Chartboost.cacheInterstitial(CBLocation.LOCATION_GAME_SCREEN);
//            }
//            Chartboost.showInterstitial(CBLocation.LOCATION_GAME_SCREEN);
            mInterstitialAd.show();
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();

                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        mInterstitialAd.show();
                    }
                });
            } else {
                requestNewInterstitial();
              //  Toast.makeText(MainActivity.this, "Đang load quảng cáo. Vui lòng đợi!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Chartboost.onStart(this);
        Chartboost.cacheRewardedVideo(CBLocation.LOCATION_ACHIEVEMENTS);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Chartboost.onStop(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Chartboost.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Chartboost.onDestroy(this);
    }

    @Override
    public void onBackPressed() {
        // If an interstitial is on screen, close it.
        if (count == 0 || Calendar.getInstance().getTimeInMillis() - count > 1000) {
            count = Calendar.getInstance().getTimeInMillis();
            Toast.makeText(this, "Bấm nút back lần nữa để thoát", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();

            if (Chartboost.onBackPressed())
                return;
            else
                super.onBackPressed();
        }
    }
    private int mImageResource;

    @Override
    public void onShowRewardVideo( int imageResource) {
       // Chartboost.showRewardedVideo(CBLocation.LOCATION_ACHIEVEMENTS);
        mImageResource = imageResource;
        requestNewInterstitial();
        mInterstitialAd.show();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    DataUtils.getINSTANCE().setUnlock(mImageResource);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            DataUtils.getINSTANCE().setUnlock(mImageResource);
                        }
                    });
                        //  DataUtils.getINSTANCE().setUnlock(imageResource);
                }
            });
        } else {
            requestNewInterstitial();
            Toast.makeText(MainActivity.this, "Đang load quảng cáo. Vui lòng đợi!", Toast.LENGTH_SHORT).show();
        }
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);

    }

}
