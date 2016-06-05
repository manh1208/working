package com.superapp.guessthepresidentushistoryquiztrivia.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.analytics.HitBuilders;
import com.superapp.guessthepresidentushistoryquiztrivia.R;
import com.superapp.guessthepresidentushistoryquiztrivia.utils.DataUtils;

public class ModeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        ImageButton back = (ImageButton) findViewById(R.id.btn_mode_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageButton countdown = (ImageButton) findViewById(R.id.btn_countdown);
        countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.playSound(getApplicationContext(),R.raw.click);
                Intent intent = new Intent(getApplicationContext(), GamePlayCountdownActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ImageButton speed = (ImageButton) findViewById(R.id.btn_speed);
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUtils.playSound(getApplicationContext(),R.raw.click);
                Intent intent = new Intent(getApplicationContext(), GamePlayTocdoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DataUtils.playSound(getApplicationContext(),R.raw.click);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }

}
