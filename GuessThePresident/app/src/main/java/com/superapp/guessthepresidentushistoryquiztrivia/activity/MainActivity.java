package com.superapp.guessthepresidentushistoryquiztrivia.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.onesignal.OneSignal;
import com.samsung.android.sdk.motion.Smotion;
import com.samsung.android.sdk.motion.SmotionCall;
import com.samsung.android.sdk.motion.SmotionPedometer;
import com.superapp.guessthepresidentushistoryquiztrivia.R;
import com.superapp.guessthepresidentushistoryquiztrivia.application.AnalyticsApplication;
import com.superapp.guessthepresidentushistoryquiztrivia.utils.BaseGameUtils;
import com.superapp.guessthepresidentushistoryquiztrivia.utils.DataUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class MainActivity extends Activity  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "ResultActivity" ;
    private static final int RC_SIGN_IN = 9001;
    private static final int REQUEST_LEADERBOARD = 1994;

    private boolean mResolvingConnectionFailure = false;
    private Button btnPlay;
    private long count;
    private AdView mAdView;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_UNUSED = 5001;
    private Tracker mTracker;
    private NetworkInfo mWifi;
    private NetworkInfo mMobile;
    private ConnectivityManager connManager;
    private  Button btnSound;
    private Smotion mMotion;
    private SmotionPedometer mPedometer;
    private SmotionCall mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OneSignal.startInit(this).init();
        connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

       // ImageView iv = (ImageView) findViewById(R.id.im_guesspresident);
       // iv.setImageResource(getDrawable(2130837669));

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Main Menu");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .addApi(AppIndex.API).build();

        btnPlay = (Button) findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataUtils.getINSTANCE().isEnableSound()) {
                    MediaPlayer mp;
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.click);
                    mp.start();
                }
                Intent intent = new Intent(getApplicationContext(), ModeActivity.class);
                startActivity(intent);
                finish();
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Mode Play")
                        .build());
            }
        });


        ImageButton rank = (ImageButton) findViewById(R.id.btn_ranklist);
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.playSound(getApplicationContext(),R.raw.click);
                if (mWifi.isConnected() || mMobile.isConnected()) {
                    if (isSignedIn()) {
                        startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient),
                                RC_UNUSED);
                    } else {
                        mGoogleApiClient.connect();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Không có kết nối internet. Xin hãy bật wifi hoặc dữ liệu di động", Toast.LENGTH_SHORT).show();
//            AlertDialog myAlertDialog = taoMotAlertDialog();
//            myAlertDialog.show();
                }
            }
        });
         btnSound = (Button) findViewById(R.id.btn_Sound);

        if (DataUtils.getINSTANCE().isEnableSound()) {
            btnSound.setText("Sound: ON");
        }else{
            btnSound.setText("Sound: OFF");
        }
        checkFacebook();

    }

    private void checkFacebook(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.superapp.guessthepresidentushistoryquiztrivia",PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        mTracker.setScreenName("Main Menu");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


    public void onSoundClick(View view){

        if (!DataUtils.getINSTANCE().isEnableSound()) {
            DataUtils.playSound(getApplicationContext(),R.raw.click);
            DataUtils.getINSTANCE().setIsEnableSound(true);
            btnSound.setText("Sound: ON");
        }else{
            DataUtils.getINSTANCE().setIsEnableSound(false);
            btnSound.setText("Sound: OFF");
        }


    }
    public void OnMoreClick(View view){
        if (DataUtils.getINSTANCE().isEnableSound()) {
            DataUtils.playSound(getApplicationContext(), R.raw.click);
        }
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(intent);
        finish();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Mode Play")
                .build());
    }

    @Override
    public void onBackPressed() {
        if (count == 0 || Calendar.getInstance().getTimeInMillis() - count > 1000) {
            count = Calendar.getInstance().getTimeInMillis();
            Toast.makeText(this, "Bấm nút back lần nữa để thoát", Toast.LENGTH_SHORT).show();
        } else {
            DataUtils.playSound(getApplicationContext(),R.raw.click);
        super.onBackPressed();
        }
    }

    private boolean isSignedIn() {
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                BaseGameUtils.showActivityResultError(this, requestCode, resultCode, R.string.signin_other_error);
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed(): attempting to resolve");
        if (mResolvingConnectionFailure) {
            Log.d(TAG, "onConnectionFailed(): already resolving");
            return;
        }
        if (!BaseGameUtils.resolveConnectionFailure(this, mGoogleApiClient, connectionResult,
                RC_SIGN_IN, getString(R.string.signin_other_error))) {
            mResolvingConnectionFailure = false;
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        Player p = Games.Players.getCurrentPlayer(mGoogleApiClient);
        if (p == null) {
            Log.w(TAG, "mGamesClient.getCurrentPlayer() is NULL!");
            // displayName = "???";
        } else {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient),
                    RC_UNUSED);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended(): attempting to connect");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
