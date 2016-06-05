package com.superapp.kingscupdrinkingamefunpartyrock.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.superapp.kingscupdrinkingamefunpartyrock.R;
import com.superapp.kingscupdrinkingamefunpartyrock.adapter.CardRuleAdapter;
import com.superapp.kingscupdrinkingamefunpartyrock.application.AnalyticsApplication;
import com.superapp.kingscupdrinkingamefunpartyrock.model.Card;
import com.superapp.kingscupdrinkingamefunpartyrock.utils.DataUtils;

import java.util.List;

public class CardRuleActivity extends AppCompatActivity {
    private List<Card> cardList;
    private TextView mCardSubject;
    private TextView mCardContent;
    private AdView mAdView;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_rule);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Card Rule");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Card Rule");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCardSubject = (TextView) findViewById(R.id.tv_cardrule_subject);
        mCardContent = (TextView) findViewById(R.id.tv_cardrule_content);
        ListView listView = (ListView) findViewById(R.id.lv_cardrule_listcard);
        cardList = DataUtils.getINSTANCE(CardRuleActivity.this).getLanguage().getCards();
        CardRuleAdapter cardRuleAdapter = new CardRuleAdapter(this,R.id.lv_cardrule_listcard,cardList,CardRuleActivity.this);
        DataUtils.getINSTANCE(CardRuleActivity.this).playSound(CardRuleActivity.this);
        Card card = cardList.get(0);
        String str = card.getCardContent();
        String subject="";
        String content="";
        if (str.contains(":")){
            String[] s = str.split(":");
            subject = s[0];
            content = s[1];
        }else{
            subject = "Subject";
            content = str;
        }
        mCardContent.setText(content);
        mCardSubject.setText(subject);
        listView.setAdapter(cardRuleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataUtils.getINSTANCE(CardRuleActivity.this).playSound(CardRuleActivity.this);
                Card card = cardList.get(position);
                String str = card.getCardContent();
                String subject="";
                String content="";
                if (str.contains(":")){
                    String[] s = str.split(":");
                    subject = s[0];
                    content = s[1];
                }else{
                    subject = "Subject";
                    content = str;
                }
                mCardContent.setText(content);
                mCardSubject.setText(subject);
            }
        });
    }

}
