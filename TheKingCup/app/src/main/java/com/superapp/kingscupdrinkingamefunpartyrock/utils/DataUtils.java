package com.superapp.kingscupdrinkingamefunpartyrock.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.google.gson.Gson;
import com.superapp.kingscupdrinkingamefunpartyrock.R;
import com.superapp.kingscupdrinkingamefunpartyrock.model.Language;
import com.superapp.kingscupdrinkingamefunpartyrock.model.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by ManhNV on 3/8/2016.
 */
public class DataUtils {
    private static DataUtils INSTANCE =null;
    private int mCurrentLanguage;
    private HashMap<Integer,Language> mListLanguage;
    private List<String> mListLanguageName;
    private Context mContext;
    private HashMap<Integer, String> mCards;
    private List<Player> players;
    private boolean isEnableSound;
    private MediaPlayer mp;


    private DataUtils(Context context){
        mContext = context;
        CreateListLanguage(getJsonObject());
        CreateCards();
        mp = MediaPlayer.create(context,R.raw.click);


    }
    public static synchronized DataUtils getINSTANCE(Context context){
        if (INSTANCE==null){
            INSTANCE = new DataUtils(context);
        }
        return INSTANCE;
    }

    public static int random(int min, int max){
        Random r = new Random();
        int ran = r.nextInt();
        ran = ran> 0 ? ran : ran*-1;
        ran = min + ran %(max-min+1);
        return ran;
    }

    private String getJsonObject(){
        String json = "";

        Log.d("DataUtils", "Get JSON data language");
        json = loadJSONFromAsset("json/language.json");
        return json;
    }

    private String loadJSONFromAsset(String jsonFile) {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void CreateListLanguage(String json){
        mListLanguage = new HashMap<Integer,Language>();
        mListLanguageName = new ArrayList<String>();
        List<Language> result = new ArrayList<Language>();
        try {
            JSONObject jsonArrObject = new JSONObject(json);
            JSONArray jsonArr = jsonArrObject.getJSONArray("languageList");
            JSONObject jsonObj = null;
            Gson gson = new Gson();
            for (int i = 0; i < jsonArr.length(); i++) {
                jsonObj = jsonArr.getJSONObject(i);
                result.add(gson.fromJson(jsonObj.toString(), Language.class));
            }

            for (Language item: result) {
                mListLanguageName.add(item.getName());
                mListLanguage.put(item.getId(),item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void CreateCards(){
        mCards = new HashMap<Integer,String>();
        mCards.put(R.drawable.motbich, "01bich");
        mCards.put(R.drawable.motchuon, "01chuon");
        mCards.put(R.drawable.motco, "01co");
        mCards.put(R.drawable.motro, "01ro");
        mCards.put(R.drawable.haibich, "02bich");
        mCards.put(R.drawable.haichuon, "02chuon");
        mCards.put(R.drawable.haico, "02co");
        mCards.put(R.drawable.hairo, "02ro");
        mCards.put(R.drawable.babich, "03bich");
        mCards.put(R.drawable.bachuon, "03chuon");
        mCards.put(R.drawable.baco, "03co");
        mCards.put(R.drawable.baro, "03ro");
        mCards.put(R.drawable.bonbich, "04bich");
        mCards.put(R.drawable.bonchuon, "04chuon");
        mCards.put(R.drawable.bonco, "04co");
        mCards.put(R.drawable.bonro, "04ro");
        mCards.put(R.drawable.nambich, "05bich");
        mCards.put(R.drawable.namchuon, "05chuon");
        mCards.put(R.drawable.namco, "05co");
        mCards.put(R.drawable.namro, "05ro");
        mCards.put(R.drawable.saubich, "06bich");
        mCards.put(R.drawable.sauchuon, "06chuon");
        mCards.put(R.drawable.sauco, "06co");
        mCards.put(R.drawable.sauro, "06ro");
        mCards.put(R.drawable.baybich, "07bich");
        mCards.put(R.drawable.baychuon, "07chuon");
        mCards.put(R.drawable.bayco, "07co");
        mCards.put(R.drawable.bayro, "07ro");
        mCards.put(R.drawable.tambich, "08bich");
        mCards.put(R.drawable.tamchuon, "08chuon");
        mCards.put(R.drawable.tamco, "08co");
        mCards.put(R.drawable.tamro, "08ro");
        mCards.put(R.drawable.chinbich, "09bich");
        mCards.put(R.drawable.chinchuon, "09chuon");
        mCards.put(R.drawable.chinco, "09co");
        mCards.put(R.drawable.chinro, "09ro");
        mCards.put(R.drawable.muoibich, "10bich");
        mCards.put(R.drawable.muoichuon, "10chuon");
        mCards.put(R.drawable.muoico, "10co");
        mCards.put(R.drawable.muoiro, "10ro");
        mCards.put(R.drawable.jbich, "11bich");
        mCards.put(R.drawable.jchuon, "11chuon");
        mCards.put(R.drawable.jco, "11co");
        mCards.put(R.drawable.jro, "11ro");
        mCards.put(R.drawable.qbich, "12bich");
        mCards.put(R.drawable.qchuon, "12chuon");
        mCards.put(R.drawable.qco, "12co");
        mCards.put(R.drawable.qro, "12ro");
        mCards.put(R.drawable.kbich, "13bich");
        mCards.put(R.drawable.kchuon, "13chuon");
        mCards.put(R.drawable.kco, "13co");
        mCards.put(R.drawable.kro, "13ro");
    }

    public Language getLanguage(){
        return mListLanguage.get(mCurrentLanguage);
    }
    public void setmCurrentLanguage(int languageId){
        this.mCurrentLanguage = languageId;
    }

    public HashMap<Integer,Language> getListLanguage(){
        return mListLanguage;
    }

    public List<String> getListLanguageName() {
        return mListLanguageName;
    }

    public HashMap<Integer, String> getCards() {
        return mCards;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void playSound(Context context){
        if (DataUtils.getINSTANCE(context).isEnableSound()) {
            mp.start();
        }
    }

    public boolean isEnableSound() {
        return isEnableSound;
    }

    public void setIsEnableSound(boolean isEnableSound) {
        this.isEnableSound = isEnableSound;
    }
}
