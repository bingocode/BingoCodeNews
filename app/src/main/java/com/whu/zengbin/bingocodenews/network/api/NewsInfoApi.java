package com.whu.zengbin.bingocodenews.network.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zengbin on 2018/2/15.
 */

public interface NewsInfoApi {

    @GET("data/{category}/10/{page}")
    Observable<ResponseBody> getNewsInfoList(@Path("category") String catagory, @Path("page") int page);

    @GET("search/query/{content}/category/{category}/count/10/page/{page}")
    Observable<ResponseBody> getNewsSearchResultList(@Path("category") String catagory, @Path("page") int page, @Path("content") String content);
}
