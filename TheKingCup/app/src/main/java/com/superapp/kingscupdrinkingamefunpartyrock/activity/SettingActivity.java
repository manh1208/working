package com.superapp.kingscupdrinkingamefunpartyrock.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.superapp.kingscupdrinkingamefunpartyrock.R;
import com.superapp.kingscupdrinkingamefunpartyrock.utils.DataUtils;

public class SettingActivity extends AppCompatActivity {
    private final Context mContext = SettingActivity.this;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(DataUtils.getINSTANCE(mContext).getLanguage().getSetting());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btnCardRule = (Button) findViewById(R.id.btn_setting_rule);
        btnCardRule.setText(DataUtils.getINSTANCE(mContext).getLanguage().getSettingCardRule());
        btnCardRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.getINSTANCE(mContext).playSound(mContext);
                Intent intent = new Intent(SettingActivity.this, CardRuleActivity.class);
                startActivity(intent);

            }
        });
        Button btnLanguage = (Button) findViewById(R.id.btn_setting_language);
        btnLanguage.setText(DataUtils.getINSTANCE(mContext).getLanguage().getSettingLanguage());
        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.getINSTANCE(mContext).playSound(mContext);
                Intent intent = new Intent(SettingActivity.this, LanguageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnFeedback = (Button) findViewById(R.id.btn_setting_feedback);
        btnFeedback.setText(DataUtils.getINSTANCE(mContext).getLanguage().getSettingFeedback());
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.getINSTANCE(mContext).playSound(mContext);
                Uri uri;
                Intent intent;
                uri = Uri.parse("https://www.facebook.com/supercoolappteam");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });

        Button btnHotGame = (Button) findViewById(R.id.btn_setting_hotgame);
        btnHotGame.setText(DataUtils.getINSTANCE(mContext).getLanguage().getSettingHotGame());
        btnHotGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.getINSTANCE(mContext).playSound(mContext);
                Uri uri;
                Intent intent;
//                uri = Uri.parse("http://www.bestappsforphone.com/gameofthemonth"); //GP
//                uri = Uri.parse("http://www.bestappsforphone.com/appotagameofthemonth"); //APPOTA
                uri = Uri.parse("http://www.bestappsforphone.com/samsunggameofthemonth"); //SamSung
//                uri = Uri.parse("http://www.bestappsforphone.com/kindlegameofthemonth"); //Kindle

                intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });

        Button btnLikeUs = (Button) findViewById(R.id.btn_setting_likeus);
        btnLikeUs.setText(DataUtils.getINSTANCE(mContext).getLanguage().getSettingLikeUs());
        btnLikeUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.getINSTANCE(mContext).playSound(mContext);
                Uri uri;
                Intent intent;
                uri = Uri.parse("https://www.facebook.com/supercoolappteam"); //GP
                intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });

        Button btnOtherApp = (Button) findViewById(R.id.btn_setting_otherapp);
        btnOtherApp.setText(DataUtils.getINSTANCE(mContext).getLanguage().getSettingOthersApps());
        btnOtherApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.getINSTANCE(mContext).playSound(mContext);
                Uri uri;
                Intent intent;
//                uri = Uri.parse("http://www.bestappsforphone.com/supercoolappteam"); //GP
//                uri = Uri.parse("http://www.bestappsforphone.com/appotagameofthemonth"); //APPOTA
                uri = Uri.parse("http://www.bestappsforphone.com/samsunggameofthemonth"); //Samsung
//                uri = Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.supercoolapps.teenagemutantninjaturtlesgoldminerushmedespicablemonster&showAll=1"); //Kindle
                intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }
        });
    }

}
