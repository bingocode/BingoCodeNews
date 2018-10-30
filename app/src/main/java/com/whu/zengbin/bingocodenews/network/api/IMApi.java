package com.whu.zengbin.bingocodenews.network.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 创建时间: 2018/10/23 11:08 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public interface IMApi {
  @GET("fetchMsg")
  Observable<ResponseBody> fetchRecentMsg(@Query("page") int page);
}
