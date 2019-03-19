package com.whu.zengbin.bingocodenews.common;

/**
 * Created by zengbin on 2018/2/14.
 */

public class ConstraintUtil {
    public static final String ANDROID = "Android";
    public static final String WEB = "前端";
    public static final String VIDEO = "休息视频";
    public static final String PHOTO= "福利";
    public static final String ID = "_id";
    public static final String GANHUOID = "ganhuo_id";
    public static final String CREATEAT = "createdAt";
    public static final String DESC = "desc";
    public static final String IMAGES = "images";
    public static final String PUBLISHAT = "publishedAt";
    public static final String SOURCE = "source";
    public static final String TYPE = "type";
    public static final String URL = "url";
    public static final String USED = "used";
    public static final String WHO = "who";
    public static final String RESULTS = "results";
    public static final String IMG_SUFFIX_WIDTH = "?imageView2/0/w/";
    public static final String BASE_IP1 = "192.168.1.13"; // 家本地地址
    public static final String BASE_IP2 = "10.33.138.61"; // 公司本地地址
    public static final String BASE_IP3 = "39.107.71.82"; // 线上地址
    public static final String BASE_IP = BASE_IP3;


    public static String CATAGORY = ANDROID;
    public static final String BASE_NEWS_URL = "http://gank.io/api/"; //http://gank.io/api/data/Android/10/1
   //http://gank.io/api/search/query/listview/category/Android/count/10/page/1

    public static final String BASE_IM_URL = "http://"+ BASE_IP + ":8080/";

    public static int ANDROID_FLAG = 0;
    public static int WEB_FLAG = 1;
    public static int VEDIO_FLAG = 2;
    public static int PHOTO_FLAG = 3;



}
