package com.superapp.guessthepresidentushistoryquiztrivia.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.superapp.guessthepresidentushistoryquiztrivia.R;
import com.superapp.guessthepresidentushistoryquiztrivia.utils.DataUtils;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ImageButton back = (ImageButton) findViewById(R.id.btn_setting_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DataUtils.playSound(getApplicationContext(),R.raw.click);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void onRateClick(View v){
        DataUtils.playSound(getApplicationContext(),R.raw.click);
        Uri uri;
        Intent intent;
        uri = Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.superapp.guessthepresidentushistoryquiztrivia");
        //uri=Uri.parse("http://appvn.com/android/details?id=com.superapp.guessthepresidentushistoryquiztrivia");
        //uri=Uri.parse("http://play.google.com/store/apps/details?id=com.superapp.guessthepresidentushistoryquiztrivia");
        // uri = Uri.parse("http://apps.samsung.com/mercury/topApps/topAppsDetail.as?productId=000000758087");
        intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }
    public void onLikeUsClick(View v){
        DataUtils.playSound(getApplicationContext(),R.raw.click);
        Uri uri;
        Intent intent;
        uri = Uri.parse("https://www.facebook.com/supercoolappteam");
        intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }
    public void onAboutUsClick(View v){
        DataUtils.playSound(getApplicationContext(),R.raw.click);
        Uri uri;
        Intent intent;
        uri = Uri.parse("http://www.bestappsforphone.com");
        intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }
    public void onHotGameClick(View v){
        DataUtils.playSound(getApplicationContext(),R.raw.click);
        Uri uri;
        Intent intent;
       //uri=Uri.parse("http://www.bestappsforphone.com/appotagameofthemonth");
        uri =Uri.parse("http://www.bestappsforphone.com/kindlegameofthemonth");
       // uri=Uri.parse("http://www.bestappsforphone.com/gameofthemonth");
      //   uri = Uri.parse("http://www.bestappsforphone.com/samsunggameofthemonth");
        intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }
}
