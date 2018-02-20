package com.whu.zengbin.bingocodenews.network;

import com.whu.zengbin.bingocodenews.bean.NewsInfo;
import com.whu.zengbin.bingocodenews.common.ConstraintUtil;

import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zengbin on 2018/2/15.
 */

public class NetWorkMrg {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ConstraintUtil.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static void requestNewsInfo(String catagory, int page, retrofit2.Callback<ResponseBody> callback) {
        NewsInfoService service = retrofit.create(NewsInfoService.class);
        Call<ResponseBody> call = service.getHomeNewsInfoList(catagory,page);
        call.enqueue(callback);
    }

    public static void requestNewsSearchList(String catagory, int page, String content, retrofit2.Callback<ResponseBody> callback) {
        NewsInfoService service = retrofit.create(NewsInfoService.class);
        Call<ResponseBody> call = service.getSearchResultList(catagory,page,content);
        call.enqueue(callback);
    }

}
