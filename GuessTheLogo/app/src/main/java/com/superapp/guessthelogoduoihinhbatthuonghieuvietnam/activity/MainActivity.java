package com.superapp.guessthelogoduoihinhbatthuonghieuvietnam.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.onesignal.OneSignal;
import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.ads.banner.RevMobBanner;
import com.samsung.android.sdk.motion.Smotion;
import com.samsung.android.sdk.motion.SmotionCall;
import com.samsung.android.sdk.motion.SmotionPedometer;
import com.superapp.guessthelogoduoihinhbatthuonghieuvietnam.R;
import com.superapp.guessthelogoduoihinhbatthuonghieuvietnam.application.AnalyticsApplication;
import com.superapp.guessthelogoduoihinhbatthuonghieuvietnam.utils.BaseGameUtils;

import java.util.Calendar;

public class MainActivity extends Activity  {
    private static final String TAG = "ResultActivity" ;
    private static final int RC_SIGN_IN = 9001;
    private static final int REQUEST_LEADERBOARD = 1994;

    private boolean mResolvingConnectionFailure = false;
    private ImageButton btnPlayCountdown;
    private ImageButton btnPlayTocdo;
    private long count;
    private static final int RC_UNUSED = 5001;
    private Tracker mTracker;
    private NetworkInfo mWifi;
    private NetworkInfo mMobile;
    private ConnectivityManager connManager;

    private Smotion mMotion;
    private SmotionPedometer mPedometer;
    private SmotionCall mCall;

    private RevMob revmob;
    private Activity currentActivity;
    private RevMobBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OneSignal.startInit(this).init();
        connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mMobile = connManager .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        String[] s = {""};


        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Main Menu");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        currentActivity = this;
        revmob = RevMob.startWithListener(this, new RevMobAdsListener() {
            @Override
            public void onRevMobSessionIsStarted() {
                revmob.showBanner(currentActivity);
                showBanner();
            }
        });

        showBanner();
        btnPlayCountdown = (ImageButton) findViewById(R.id.btnPlayCountdown);
        btnPlayCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp;
                mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mp.start();
                Intent intent = new Intent(getApplicationContext(),GamePlayCountdownActivity.class);
                startActivity(intent);
                finish();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Play Countdown")
                        .build());

            }
        });
        btnPlayTocdo = (ImageButton) findViewById(R.id.btnPlayTocdo);
        btnPlayTocdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp;
                mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mp.start();
                Intent intent = new Intent(getApplicationContext(), GamePlayTocdoActivity.class);
                startActivity(intent);
                finish();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Play Tốc độ")
                        .build());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        mTracker.setScreenName("Main Menu");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    public void onRankClick(View view){
        MediaPlayer mp;
        mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
        mp.start();
        Uri uri;
        Intent intent;
       //uri = Uri.parse("http://appvn.com/android/details?id=com.superapp.guessthelogoduoihinhbatthuonghieuvietnam");
        uri=Uri.parse("http://apps.samsung.com/mercury/topApps/topAppsDetail.as?productId=");
       // uri=Uri.parse("http://www.bestappsforphone.com/samsunggameofthemonth");
        intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }
    public void onHotGameClick(View view){
        MediaPlayer mp;
        mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
        mp.start();
        Uri uri;
        Intent intent;
        uri=Uri.parse("http://www.bestappsforphone.com/samsunggameofthemonth");
    //    uri = Uri.parse("http://www.bestappsforphone.com/appotagameofthemonth");
        intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }
    public void onFacebookClick(View view){
        MediaPlayer mp;
        mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
        mp.start();
        Uri uri;
        Intent intent;
        uri = Uri.parse("https://www.facebook.com/supercoolappteam");
        intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }
    public void onContactClick(View view){
        MediaPlayer mp;
        mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
        mp.start();
        Uri uri;
        Intent intent;
        uri = Uri.parse("http://www.bestappsforphone.com");
        intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (count == 0 || Calendar.getInstance().getTimeInMillis() - count > 1000) {
            count = Calendar.getInstance().getTimeInMillis();
            Toast.makeText(this, "Bấm nút back lần nữa để thoát", Toast.LENGTH_SHORT).show();
        } else {
        super.onBackPressed();
        }
    }

    void showBanner() {
        banner = revmob.createBanner(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup view = (ViewGroup) findViewById(R.id.main_banner);
                view.addView(banner);
            }
        });
    }
}
