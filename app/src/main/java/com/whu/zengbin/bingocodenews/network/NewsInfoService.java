package com.whu.zengbin.bingocodenews.network;

import com.whu.zengbin.bingocodenews.bean.NewsInfo;

import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by zengbin on 2018/2/15.
 */

public interface NewsInfoService {
    @GET("data/{category}/10/{page}")
    Call<ResponseBody> getHomeNewsInfoList(@Path("category") String catagory, @Path("page") int page);

    @GET("search/query/{content}/category/{category}/count/10/page/{page}")
    Call<ResponseBody> getSearchResultList(@Path("category") String catagory, @Path("page") int page, @Path("content") String content);
}
