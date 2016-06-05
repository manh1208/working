package com.superapp.guessthepresidentushistoryquiztrivia.utils;



import android.content.Context;
import android.media.MediaPlayer;

import com.superapp.guessthepresidentushistoryquiztrivia.R;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by ManhNV on 3/8/2016.
 */
public class DataUtils {
    private static DataUtils INSTANCE =null;
    private boolean isEnableSound;
    private HashMap<Integer,String> mlistPresident = new HashMap<Integer,String>();

    public HashMap<Integer, String> getMlistPresident() {
        return mlistPresident;
    }

    public void setMlistPresident(HashMap<Integer, String> mlistPresident) {
        this.mlistPresident = mlistPresident;
    }

    public boolean isEnableSound() {
        return isEnableSound;
    }

    public void setIsEnableSound(boolean isEnableSound) {
        this.isEnableSound = isEnableSound;
    }

    private DataUtils(){
        mlistPresident = new HashMap<Integer,String>();
        getListLogo();
        isEnableSound=true;
    }
    public static synchronized DataUtils getINSTANCE(){
        if (INSTANCE==null){
            INSTANCE = new DataUtils();
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

    private void getListLogo(){
        mlistPresident.put(R.drawable.abrahamlincoln,"Abraham Lincoln");
        mlistPresident.put(R.drawable.andrewjackson,"Andrew Jackson");
        mlistPresident.put(R.drawable.andrewjohnson,"Andrew Johnson");
        mlistPresident.put(R.drawable.barackobama,"Barack Obama");
        mlistPresident.put(R.drawable.benjaminharrison,"Benjamin Harrison");
        mlistPresident.put(R.drawable.billclinton,"Bill Clinton");
        mlistPresident.put(R.drawable.calvincoolidge,"Calvin Coolidge");
        mlistPresident.put(R.drawable.chesteraarthur,"Chester A. Arthur");
        mlistPresident.put(R.drawable.dwightdeisenhower,"Dwight D. Eisenhower");
        mlistPresident.put(R.drawable.franklindroosevelt,"Franklin D. Roosevelt");
        mlistPresident.put(R.drawable.franklinpierce,"Franklin Pierce");
        mlistPresident.put(R.drawable.georgehwbush,"George H. W. Bush");
        mlistPresident.put(R.drawable.georgewashington,"George Washington");
        mlistPresident.put(R.drawable.georgewbush,"George W. Bush");
        mlistPresident.put(R.drawable.geraldford,"Gerald Ford");
        mlistPresident.put(R.drawable.grovercleveland,"Grover Cleveland");
        mlistPresident.put(R.drawable.harrystruman,"Harry S. Truman");
        mlistPresident.put(R.drawable.herberthoover,"Herbert Hoover");
        mlistPresident.put(R.drawable.jamesagarfield,"James A. Garfield");
        mlistPresident.put(R.drawable.jamesbuchanan,"James Buchanan");
        mlistPresident.put(R.drawable.jameskpolk,"James K  Polk");
        mlistPresident.put(R.drawable.jamesmadison,"James Madison");
        mlistPresident.put(R.drawable.jamesmonroe,"James Monroe");
        mlistPresident.put(R.drawable.jimmycarter,"Jimmy Carter");
        mlistPresident.put(R.drawable.johnadams,"John Adams");
        mlistPresident.put(R.drawable.johnfkennedy,"John F. Kennedy");
        mlistPresident.put(R.drawable.johntyler,"John Tyler");
        mlistPresident.put(R.drawable.kohnquincyadams,"John Quincy Adams");
        mlistPresident.put(R.drawable.lyndonbjohnson,"Lyndon B. Johnson");
        mlistPresident.put(R.drawable.martinvanburen,"Martin Van Buren");
        mlistPresident.put(R.drawable.millardfillmore,"Millard Fillmore");
        mlistPresident.put(R.drawable.richardnixon,"Richard Nixon");
        mlistPresident.put(R.drawable.ronaldreagan,"Ronald Reagan");
        mlistPresident.put(R.drawable.rutherfordbhayes,"Rutherford B. Hayes");
        mlistPresident.put(R.drawable.theodoreroosevelt,"Theodore Roosevelt");
        mlistPresident.put(R.drawable.thomasjefferson,"Thomas Jefferson");
        mlistPresident.put(R.drawable.ulyssessgrant,"Ulysses S. Grant");
        mlistPresident.put(R.drawable.warrengharding,"Warren G. Harding");
        mlistPresident.put(R.drawable.williamhenryharrison,"William Henry Harrison");
        mlistPresident.put(R.drawable.williamhowardtaft,"William Howard Taft");
        mlistPresident.put(R.drawable.williammckinley,"William McKinley");
        mlistPresident.put(R.drawable.woodrowwilson,"Woodrow Wilson");
        mlistPresident.put(R.drawable.zacharytaylor,"Zachary Taylor");
    }

    public static void playSound(Context context,int soundResource){
        if (DataUtils.getINSTANCE().isEnableSound()) {
            MediaPlayer mp;
            mp = MediaPlayer.create(context, soundResource);
            mp.start();
        }
    }

}
