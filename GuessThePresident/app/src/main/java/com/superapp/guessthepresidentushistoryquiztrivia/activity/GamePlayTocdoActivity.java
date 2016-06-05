package com.superapp.guessthepresidentushistoryquiztrivia.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.superapp.guessthepresidentushistoryquiztrivia.R;
import com.superapp.guessthepresidentushistoryquiztrivia.application.AnalyticsApplication;
import com.superapp.guessthepresidentushistoryquiztrivia.utils.DataUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by ManhNV on 3/8/2016.
 */
public class GamePlayTocdoActivity extends Activity implements View.OnClickListener {
    private final int MAXTIME = 300;
    private HashMap<Integer, String> mListLogo;
    private List<Integer> mListImageLogo;
    private ImageView mIVLogo;
    private Button btnAnswer1;
    private Button btnAnswer2;
    private Button btnAnswer3;
    private TextView mTxtScore;
    private int mScore;
    private int mCurrentPosition;
    private HashMap<Integer, Boolean> mListButton;
    private SeekBar bar;
    private CountDownTimer countdown;
    private MediaPlayer mp;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplaytocdo);
        init();
        reset();

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // Log.i(TAG, "Setting screen name: Game");
        mTracker.setScreenName("Game Play Count Down");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void init() {
        mListLogo = DataUtils.getINSTANCE().getMlistPresident();
        mListImageLogo = new ArrayList<>(mListLogo.keySet());
        mIVLogo = (ImageView) findViewById(R.id.ivLogo);
        btnAnswer1 = (Button) findViewById(R.id.btnAnswer1);
        btnAnswer2 = (Button) findViewById(R.id.btnAnswer2);
        btnAnswer3 = (Button) findViewById(R.id.btnAnswer3);
        btnAnswer1.setOnClickListener(this);
        btnAnswer2.setOnClickListener(this);
        btnAnswer3.setOnClickListener(this);
        mTxtScore = (TextView) findViewById(R.id.txtScore);
        mListButton = new HashMap<Integer, Boolean>();
        mListButton.put(R.id.btnAnswer1, false);
        mListButton.put(R.id.btnAnswer2, false);
        mListButton.put(R.id.btnAnswer3, false);
        bar = (SeekBar) findViewById(R.id.pbTimer);
        bar.setProgress(MAXTIME);
        bar.setEnabled(false);
    }

    private void reset() {
        Collections.shuffle(mListImageLogo);
        mScore = 0;
        mTxtScore.setText("" + mScore);
        mCurrentPosition = 0;
        refreshQuestion();
        if (countdown != null) {
            countdown.cancel();
        }
        startTime();
    }

    private int random(int min, int max) {
        Random r = new Random();
        int ran = r.nextInt();
        ran = ran > 0 ? ran : ran * -1;
        ran = min + ran % (max - min + 1);
        return ran;

    }

    private void finishGame() {
        countdown.cancel();
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        intent.putExtra("Score", mScore);
        intent.putExtra("TypeGame", 2);
        startActivity(intent);
        finish();
    }

    private void startTime() {
        if (countdown != null) {
            countdown.cancel();
        }
        countdown = new CountDownTimer(MAXTIME * 10, 10) {

            @Override
            public void onTick(long millisUntilFinished) {
                bar.setProgress((int) (millisUntilFinished / 10));
            }

            @Override
            public void onFinish() {
                DataUtils.playSound(getApplicationContext(), R.raw.timeout);
                finishGame();
            }
        };
        countdown.start();
    }

    private void refreshQuestion() {
        mIVLogo.setImageResource(mListImageLogo.get(mCurrentPosition));
        List<Integer> mListButtonText = new ArrayList<Integer>(mListButton.keySet());
        int rightAnswerPosition = random(1, mListButtonText.size());
        int firstWrongPosition=-1;
        for (int i = 0; i < mListButtonText.size(); i++) {
            Button button = (Button) findViewById(mListButtonText.get(i));
            button.setBackgroundColor(Color.parseColor("#004096"));
            if (i + 1 == rightAnswerPosition) {
                mListButton.put(mListButtonText.get(i), true);
                ((Button) findViewById(mListButtonText.get(i))).setText(mListLogo.get(mListImageLogo.get(mCurrentPosition)));
            } else {
                mListButton.put(mListButtonText.get(i), false);
                int wrongAnswer;
                do {
                    wrongAnswer = random(1, mListImageLogo.size());
                }while (wrongAnswer-1==mCurrentPosition || wrongAnswer==firstWrongPosition);
                firstWrongPosition =wrongAnswer;
                ((Button) findViewById(mListButtonText.get(i))).setText(mListLogo.get(mListImageLogo.get(wrongAnswer - 1)));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (countdown != null) {
            countdown.cancel();
        }

        int id = v.getId();
        if (mListButton.containsKey(id)) {
            Button button = (Button) findViewById(id);
            button.setBackgroundColor(Color.parseColor("#ce1720"));
            if (mListButton.get(id)) {
                mScore++;
                mTxtScore.setText("" + mScore);
                mCurrentPosition++;
                if (mCurrentPosition>=mListImageLogo.size()){
                    finishGame();
                }else {
                    refreshQuestion();
                    startTime();
                }
            } else {
                DataUtils.playSound(getApplicationContext(), R.raw.ohno);
                finishGame();
                //Toast.makeText(GamePlayCountdownActivity.this, "Sai rồi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        if (mp != null && mp.isPlaying()) {
            mp.stop();
        }
        DataUtils.playSound(getApplicationContext(), R.raw.click);
        countdown.cancel();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        reset();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countdown!=null){
            countdown.cancel();
        }

    }
}

