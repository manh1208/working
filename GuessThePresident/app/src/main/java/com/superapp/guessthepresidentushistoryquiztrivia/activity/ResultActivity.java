package com.superapp.guessthepresidentushistoryquiztrivia.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.superapp.guessthepresidentushistoryquiztrivia.R;
import com.superapp.guessthepresidentushistoryquiztrivia.application.AnalyticsApplication;
import com.superapp.guessthepresidentushistoryquiztrivia.utils.BaseGameUtils;
import com.superapp.guessthepresidentushistoryquiztrivia.utils.DataUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by ManhNV on 3/8/2016.
 */
public class ResultActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "ResultActivity";
    private static final int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private int mHighScore;
    private int type;
    MediaPlayer mp;
    private AdView mAdView;
    private GoogleApiClient mGoogleApiClient;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        requestNewInterstitial();

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        Log.i(TAG, "Setting screen name: Game");
        mTracker.setScreenName("Kết quả");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        int mScore = getIntent().getIntExtra("Score", 0);
        type = getIntent().getIntExtra("TypeGame", 1);
        TextView mYourScore = (TextView) findViewById(R.id.txtYourScore);
        mYourScore.setText("" + mScore);
        TextView mtxtHighScore = (TextView) findViewById(R.id.txtHighScore);
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

            mHighScore = mScore;
            if (mWifi.isConnected() || mMobile.isConnected()) {
                if (isSignedIn()) {
                    if (type == 2) {
                        Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.LEADERBOARD_Tocdo_ID),
                                mHighScore);
                    } else {
                        Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.LEADERBOARD_DemNguoc_ID),
                                mHighScore);
                    }
                } else {
                    mGoogleApiClient.connect();
                }
            }
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
        ImageButton mPlayAgain = (ImageButton) findViewById(R.id.btnPlayAgain);
        mPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent;
                if (type == 1) {
                    intent = new Intent(getApplicationContext(), GamePlayCountdownActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), GamePlayTocdoActivity.class);
                }

                int rand = DataUtils.random(1, 4);
                Log.d("Random", rand + "");
                if (rand == 1) {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                super.onAdClosed();
                                DataUtils.playSound(getApplicationContext(), R.raw.doitagain);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                mInterstitialAd.show();
                            }
                        });
                    } else {
                        DataUtils.playSound(getApplicationContext(), R.raw.doitagain);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    DataUtils.playSound(getApplicationContext(), R.raw.doitagain);
                    startActivity(intent);
                    finish();
                }

            }
        });
        ImageButton mHome = (ImageButton) findViewById(R.id.btnHome);
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.playSound(getApplicationContext(), R.raw.click);
                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                int rand = DataUtils.random(1, 3);
                Log.d("Random", rand + "");
                if (rand == 1) {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdClosed() {
                                super.onAdClosed();
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                mInterstitialAd.show();
                            }
                        });
                    } else {
                        startActivity(intent);
                        finish();
                    }

                } else {
                    startActivity(intent);
                    finish();
                }

            }
        });
        ImageButton mShare = (ImageButton) findViewById(R.id.btnShareFacebook);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.playSound(getApplicationContext(),R.raw.click);
                Bitmap bitmap = takeScreenshot();
                FacebookSdk.sdkInitialize(getApplicationContext());
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                ShareDialog shareDialog = new ShareDialog(ResultActivity.this);
                shareDialog.show(ResultActivity.this, content);

            }
        });
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);

    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mp != null && mp.isPlaying()) {
            mp.stop();
        }
        DataUtils.playSound(getApplicationContext(), R.raw.click);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart(): connecting");

    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdView != null) {
            mAdView.resume();
        }
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


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(): disconnecting");
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
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
        //Toast.makeText(ResultActivity.this, "Haha", Toast.LENGTH_SHORT).show();
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
            if (type == 2) {
                Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.LEADERBOARD_Tocdo_ID),
                        mHighScore);
            } else {
                Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.LEADERBOARD_DemNguoc_ID),
                        mHighScore);
            }
            // Toast.makeText(ResultActivity.this, "Updated new score", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended(): attempting to connect");
        mGoogleApiClient.connect();
    }


}
