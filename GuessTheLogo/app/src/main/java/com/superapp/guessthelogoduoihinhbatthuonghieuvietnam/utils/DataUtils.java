package com.superapp.guessthelogoduoihinhbatthuonghieuvietnam.utils;

import com.superapp.guessthelogoduoihinhbatthuonghieuvietnam.R;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by ManhNV on 3/8/2016.
 */
public class DataUtils {
    private static DataUtils INSTANCE =null;
    HashMap<Integer,String> mListLogo;
    private DataUtils(){
        CreateListLogo();
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

    private void CreateListLogo(){
        mListLogo = new HashMap<Integer,String>();
        mListLogo.put(R.drawable.abbott,"Abbott");
        mListLogo.put(R.drawable.abc,"ABC");
        mListLogo.put(R.drawable.airmekong,"Air Mekong");
        mListLogo.put(R.drawable.ajinomoto,"Ajinomoto");
        mListLogo.put(R.drawable.alcado,"alcado");
        mListLogo.put(R.drawable.anphuoc,"An Phước");
        mListLogo.put(R.drawable.aone,"A-One");
        mListLogo.put(R.drawable.bachma,"Bạch Mã");
        mListLogo.put(R.drawable.bhd,"BHD");
        mListLogo.put(R.drawable.bibica,"Bibica");
        mListLogo.put(R.drawable.bitas,"Bitas");
        mListLogo.put(R.drawable.bitis,"Bitis");
        mListLogo.put(R.drawable.blook,"Blook");
        mListLogo.put(R.drawable.cailan,"Cái Lân");
        mListLogo.put(R.drawable.campha,"Cẩm Phả");
        mListLogo.put(R.drawable.canifa,"Canifa");
        mListLogo.put(R.drawable.casumina,"Casumina");
        mListLogo.put(R.drawable.cgv,"CGV");
        mListLogo.put(R.drawable.cholimex,"Cholimex");
        mListLogo.put(R.drawable.chuongduong,"Chương Dương");
        mListLogo.put(R.drawable.colusa,"Colusa");
        mListLogo.put(R.drawable.daidongtien,"Đại Đồng Tiến");
        mListLogo.put(R.drawable.dhgpharma,"DHG Pharma");
        mListLogo.put(R.drawable.dutchlady,"Dutch Lady");
        mListLogo.put(R.drawable.everon,"Everon");
        mListLogo.put(R.drawable.evn,"EVN");
        mListLogo.put(R.drawable.familymart,"Family Mart");
        mListLogo.put(R.drawable.galaxycinema,"Galaxy Cinema");
        mListLogo.put(R.drawable.halida,"Halida");
        mListLogo.put(R.drawable.hellokids,"Hello Kids");
        mListLogo.put(R.drawable.hoaphat,"Hòa Phát");
        mListLogo.put(R.drawable.hoasen,"Hoa Sen");
        mListLogo.put(R.drawable.holcim,"Holcim");
        mListLogo.put(R.drawable.hudabeer,"Huda Beer");
        mListLogo.put(R.drawable.jetstart,"Jet Start");
        mListLogo.put(R.drawable.kewpie,"Kew Pie");
        mListLogo.put(R.drawable.kiemnghia,"Kiềm Nghĩa");
        mListLogo.put(R.drawable.kimdong,"Kim Đồng");
        mListLogo.put(R.drawable.kinhdo,"Kinh Đô");
        mListLogo.put(R.drawable.kirin,"Kirin");
        mListLogo.put(R.drawable.kymdan,"Kym Dan");
        mListLogo.put(R.drawable.larue,"Larue");
        mListLogo.put(R.drawable.lavie,"Lavie");
        mListLogo.put(R.drawable.lotte,"Lotte");
        mListLogo.put(R.drawable.maggi,"Maggi");
        mListLogo.put(R.drawable.megastar,"MegaStar");
        mListLogo.put(R.drawable.meiji,"Meiji");
        mListLogo.put(R.drawable.ministop,"Mini Stop");
        mListLogo.put(R.drawable.miti,"Miti");
        mListLogo.put(R.drawable.mobifone,"Mobifone");
        mListLogo.put(R.drawable.nandm,"N & M");
        mListLogo.put(R.drawable.meiji,"Meiji");
        mListLogo.put(R.drawable.neptune,"NepTune");
        mListLogo.put(R.drawable.nestle,"Nestle");
        mListLogo.put(R.drawable.nhanam,"Nhã Nam");
        mListLogo.put(R.drawable.nhaxuatbantre,"Nhà xuất bản Trẻ");
        mListLogo.put(R.drawable.nhuacholon,"Nhựa chợ lớn");
        mListLogo.put(R.drawable.nissan,"Nissan");
        mListLogo.put(R.drawable.nonson,"Nón Sơn");
        mListLogo.put(R.drawable.pepsico,"Pepsico");
        mListLogo.put(R.drawable.phuongnamfilm,"Phương Nam film");
        mListLogo.put(R.drawable.pnj,"PNJ");
        mListLogo.put(R.drawable.pt2000,"PT2000");
        mListLogo.put(R.drawable.rando,"Rando");
        mListLogo.put(R.drawable.rangdong,"Rạng Đông");
        mListLogo.put(R.drawable.sabeco,"Sabeco");
        mListLogo.put(R.drawable.saigontourist,"Sai Gon Tourist");
        mListLogo.put(R.drawable.sapuwa,"Sapuwa");
        mListLogo.put(R.drawable.sututrang,"Sư Tử Trắng");
        mListLogo.put(R.drawable.thachbich,"Thạch Bích");
        mListLogo.put(R.drawable.thanglong,"Thăng Long");
        mListLogo.put(R.drawable.thanhbuoi,"Thành Bưởi");
        mListLogo.put(R.drawable.thienlong,"Thiên Long");
        mListLogo.put(R.drawable.thoidai,"Thời Đại");
        mListLogo.put(R.drawable.tiger,"Tiger");
        mListLogo.put(R.drawable.tondonga,"Tôn Đông Á");
        mListLogo.put(R.drawable.traphaco,"Traphaco");
        mListLogo.put(R.drawable.tribeco,"Tribeco");
        mListLogo.put(R.drawable.trithuc,"Tri Thức");
        mListLogo.put(R.drawable.tuongan,"Tường An");
        mListLogo.put(R.drawable.vedan,"Vedan");
        mListLogo.put(R.drawable.vento,"Vento");
        mListLogo.put(R.drawable.vietjetair,"Việt JetAir");
        mListLogo.put(R.drawable.vietnamairlines,"Việt Nam Airlines");
        mListLogo.put(R.drawable.vietnammobile,"Việt Nam Mobile");
        mListLogo.put(R.drawable.viettel,"Viettel");
        mListLogo.put(R.drawable.viettien,"Việt Tiến");
        mListLogo.put(R.drawable.vifon,"Vifon");
        mListLogo.put(R.drawable.viglacera,"Viglacera");
        mListLogo.put(R.drawable.vinaacecook,"Vina Acecook");
        mListLogo.put(R.drawable.vinacafe,"Vinacafe");
        mListLogo.put(R.drawable.vinamilk,"Vinamilk");
        mListLogo.put(R.drawable.vinhhao,"Vĩnh Hảo");
        mListLogo.put(R.drawable.vinhtien,"Vĩnh Tiến");
        mListLogo.put(R.drawable.vissan,"Vissan");
        mListLogo.put(R.drawable.vital,"Vital");
        mListLogo.put(R.drawable.vntelecom,"VN Telecom");
        mListLogo.put(R.drawable.ximanghatien,"Xi Măng Hà Tiên");
        mListLogo.put(R.drawable.yokohama,"Yokohama");
    }

    public HashMap<Integer,String> getListLogo(){
        return mListLogo;
    }

}
