package com.superapp.guessthelogoduoihinhbatthuonghieuvietnam.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.Player;
import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.ads.interstitial.RevMobFullscreen;
import com.superapp.guessthelogoduoihinhbatthuonghieuvietnam.R;
import com.superapp.guessthelogoduoihinhbatthuonghieuvietnam.application.AnalyticsApplication;
import com.superapp.guessthelogoduoihinhbatthuonghieuvietnam.utils.BaseGameUtils;
import com.superapp.guessthelogoduoihinhbatthuonghieuvietnam.utils.DataUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import com.revmob.ads.banner.RevMobBanner;

/**
 * Created by ManhNV on 3/8/2016.
 */
public class ResultActivity extends Activity {
    private static final String TAG = "ResultActivity";
    private static final int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private int mScore;
    private TextView mYourScore;
    private ImageButton mPlayAgain;
    private ImageButton mMenu;
    private int mHighScore;
    private TextView mtxtHighScore;
    private TextView txtTextView;
    private int type;
    MediaPlayer mp;
    private static final int RC_UNUSED = 5001;
    private Tracker mTracker;
    private NetworkInfo mWifi;
    private NetworkInfo mMobile;
    private ConnectivityManager connManager;
    private RevMob revmob;
    private RevMobBanner banner;
    private Activity currentActivity;
    private RevMobFullscreen fullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mMobile = connManager .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        currentActivity = this;

        revmob = RevMob.startWithListener(this, new RevMobAdsListener() {
            @Override
            public void onRevMobSessionIsStarted() {

                Log.i("Result Activity", "Load quảng cáo");
                revmob.showFullscreen(currentActivity);
                revmob.showBanner(currentActivity);
//                Toast.makeText(ResultActivity.this, "Load Quảng cáo nè", Toast.LENGTH_SHORT).show();
                loadFullscreen();
                showBanner();
                // pre-cache it without showing it
            }
        });

        int rand = DataUtils.random(1, 2);
        Log.i("Random", rand + "");
        if (rand == 1) {
            showLoadedFullscreen();

        }
        Log.i("RevMob", "Fullscreen loaded.");
        showLoadedFullscreen();
        showBanner();


        Chartboost.startWithAppId(this, "56e0ef5c8838097182932282", "44cb6823a6981d8a35f6645b1aaa75eb4a24da3b");
        Chartboost.onCreate(this);
        Chartboost.cacheInterstitial(CBLocation.LOCATION_HOME_SCREEN);
        Chartboost.setAutoCacheAds(true);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        Log.i(TAG, "Setting screen name: Game");
        mTracker.setScreenName("Kết quả");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());



        mScore = getIntent().getIntExtra("Score", 0);
        type = getIntent().getIntExtra("TypeGame", 1);
        mYourScore = (TextView) findViewById(R.id.txtYourScore);
        mYourScore.setText("" + mScore);
        mtxtHighScore = (TextView) findViewById(R.id.txtHighScore);
        mHighScore = 0;
        String fileName = "";
        if (type == 1) {
            fileName = "bestScoreCountdown.dat";
        } else {
            fileName = "bestScoreTocdo.dat";
        }
        File f = new File(String.valueOf(getFileStreamPath(fileName)));
        if (!f.exists()) {
            try {
                FileOutputStream fou = openFileOutput(fileName, MODE_WORLD_READABLE);
                OutputStreamWriter osw = new OutputStreamWriter(fou);
                osw.write("0");
                osw.flush();
                mHighScore = 0;
                osw.close();
                fou.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(getFileStreamPath(fileName));
                br = new BufferedReader(fr);
                String s = br.readLine();

                mHighScore = Integer.parseInt(s);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Some thing is wrong", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Some thing is wrong", Toast.LENGTH_LONG).show();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(this, "Some thing is wrong", Toast.LENGTH_LONG).show();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Some thing is wrong", Toast.LENGTH_LONG).show();
                    }
                }
                if (fr != null) {

                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Some thing is wrong", Toast.LENGTH_LONG).show();
                    }
                }

            }
        }
        if (mScore > mHighScore) {
            txtTextView = (TextView) findViewById(R.id.txtTextView);
            txtTextView.setText("Điểm cao mới");
            mHighScore = mScore;

            //mtxtHighScore.setTextColor(getResources().getColor(R.color.textcoloryellow));
            try {
                f = new File(String.valueOf(getFileStreamPath(fileName)));
                if (f.exists()) {
                    f.delete();
                }
                FileOutputStream fou = openFileOutput(fileName, MODE_WORLD_READABLE);
                OutputStreamWriter osw = new OutputStreamWriter(fou);
                osw.write("" + (mScore));
                osw.flush();
                osw.close();
                fou.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        mtxtHighScore.setText("" + mHighScore);
        mPlayAgain = (ImageButton) findViewById(R.id.btnPlayAgain);
        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent;
                if (type == 1) {
                    intent = new Intent(getApplicationContext(), GamePlayCountdownActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), GamePlayTocdoActivity.class);
                }


                MediaPlayer mp;
                mp = MediaPlayer.create(getApplicationContext(), R.raw.choilaina);
                mp.start();
                startActivity(intent);
                finish();
            }
        });



        mMenu = (ImageButton) findViewById(R.id.btnMenu);
        mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp;
                mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
                mp.start();
                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                int rand = DataUtils.random(1, 3);
                Log.d("Random", rand + "");
                //  Toast.makeText(ResultActivity.this, rand+"", Toast.LENGTH_SHORT).show();
                if (rand == 1) {
                    startActivity(intent);
                    finish();

                }else{
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Chartboost.onResume(this);
        int rand = DataUtils.random(1, 1);
        Log.d("Random", rand + "");
        if (rand == 1) {
            Chartboost.showInterstitial(CBLocation.LOCATION_HOME_SCREEN);
            if (Chartboost.hasInterstitial(CBLocation.LOCATION_HOME_SCREEN)) {
                Chartboost.showInterstitial(CBLocation.LOCATION_HOME_SCREEN);
            } else {
                Chartboost.cacheInterstitial(CBLocation.LOCATION_HOME_SCREEN);
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (Chartboost.onBackPressed())
            return;
        else
            super.onBackPressed();
        if (mp != null && mp.isPlaying()) {
            mp.stop();
        }
        mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
        mp.start();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Chartboost.onStart(this);
        Chartboost.cacheInterstitial(CBLocation.LOCATION_HOME_SCREEN);
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


    public void loadFullscreen() {

        //load it with RevMob listeners to control the events fired
        fullscreen = revmob.createFullscreen(this,  new RevMobAdsListener() {
            @Override
            public void onRevMobAdReceived() {
                int rand = DataUtils.random(1, 1);
                Log.d("Random", rand + "");
                if (rand == 1) {
                    showLoadedFullscreen();

                }
                showLoadedFullscreen();
                Log.i("RevMob", "Fullscreen loaded.");
            }

            @Override
            public void onRevMobAdNotReceived(String message) {
                Log.i("RevMob", "Fullscreen not received.");
            }

            @Override
            public void onRevMobAdDismissed() {
                Log.i("RevMob", "Fullscreen dismissed.");
            }

            @Override
            public void onRevMobAdClicked() {
                Log.i("RevMob", "Fullscreen clicked.");
            }

            @Override
            public void onRevMobAdDisplayed() {
                Log.i("RevMob", "Fullscreen displayed.");
            }
        });
    }

    public void showLoadedFullscreen() {
        if(revmob.isAdLoaded()) {
            Log.i("RevMob", "FullScreen have show.");
            fullscreen.show(); // call it wherever you want to show the fullscreen ad
        } else {
            Log.i("RevMob", "Ad not loaded yet.");
        }
    }

    void showBanner() {
        banner = revmob.createBanner(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewGroup view = (ViewGroup) findViewById(R.id.banner);
                view.addView(banner);
            }
        });
    }

}
