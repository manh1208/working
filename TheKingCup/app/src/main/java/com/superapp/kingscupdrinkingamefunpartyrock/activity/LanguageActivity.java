package com.superapp.kingscupdrinkingamefunpartyrock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.superapp.kingscupdrinkingamefunpartyrock.R;
import com.superapp.kingscupdrinkingamefunpartyrock.utils.DataUtils;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ManhNV on 4/8/2016.
 */
public class LanguageActivity extends Activity {
    private List<String> mLanguages;
    private AdView mAdView;
    private long count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        
        createList();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.listview_language_item,R.id.lv_language_item,mLanguages);
        ListView listView = (ListView) findViewById(R.id.lv_language);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataUtils.getINSTANCE(getApplicationContext()).playSound(getApplicationContext());
                DataUtils.getINSTANCE(LanguageActivity.this).setmCurrentLanguage(position+1);
                Intent intent = new Intent(LanguageActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void createList(){
        mLanguages = DataUtils.getINSTANCE(LanguageActivity.this).getListLanguageName();
    }

    @Override
    public void onBackPressed() {
        if (count == 0 || Calendar.getInstance().getTimeInMillis() - count > 1000) {
            count = Calendar.getInstance().getTimeInMillis();
            Toast.makeText(this, DataUtils.getINSTANCE(this).getLanguage().getBackPress(), Toast.LENGTH_SHORT).show();
        } else {
            DataUtils.getINSTANCE(LanguageActivity.this).playSound(LanguageActivity.this);
            super.onBackPressed();
        }

    }
}
