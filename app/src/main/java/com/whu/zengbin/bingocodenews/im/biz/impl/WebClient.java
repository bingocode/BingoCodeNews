package com.whu.zengbin.bingocodenews.im.biz.impl;

import android.util.Log;
import com.whu.zengbin.bingocodenews.CodeNewsApp;
import com.whu.zengbin.bingocodenews.callback.CallBackListener;
import com.whu.zengbin.bingocodenews.common.CommonUtil;
import com.whu.zengbin.bingocodenews.common.ConstraintUtil;
import com.whu.zengbin.bingocodenews.common.JsonUtil;
import com.whu.zengbin.bingocodenews.common.LogUtil;
import com.whu.zengbin.bingocodenews.event.NewsInfo;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.bean.MsgListResult;
import com.whu.zengbin.bingocodenews.im.bean.MsgType;
import com.whu.zengbin.bingocodenews.im.tool.IMHelper;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 创建时间: 2018/10/24 11:20 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class WebClient extends WebSocketClient{
  private static final String TAG = "WebClient";
  public static final String ACTION_RECEIVE_MESSAGE = "com.jinuo.mhwang.servermanager";
  public static final String KEY_RECEIVED_DATA = "data";
  private volatile static WebClient mWebClient;
  /**
   *  路径为ws+服务器地址+服务器端设置的子路径+参数
   *  如果服务器端为https的，则前缀的ws则变为wss
   */
  private static final String mAddress = "ws://"+ ConstraintUtil.BASE_IP+":8080/websocket/";

  private WebClient(URI serverUri){
    super(serverUri, new Draft_6455());
    LogUtil.i(TAG, "new WebClient");
  }

  public static WebClient getInstance() {
    if (mWebClient == null) {
      synchronized (WebClient.class) {
        if (mWebClient == null) {
          try {
            mWebClient = new WebClient(new URI(mAddress + CommonUtil.getUniqueId()));
          } catch (URISyntaxException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return mWebClient;
  }

  @Override
  public void onOpen(ServerHandshake handshakedata) {
    LogUtil.i(TAG, "open->"+handshakedata.toString());
    CodeNewsApp.getInstance().getmIMImpl().fetchIMMsgs(0, new CallBackListener<ResponseBody>() {
      @Override
      public void onResponse(ResponseBody response) {
        try {
          String jsonStr = response.string();
          LogUtil.i(TAG, "open response = " + jsonStr);
          MsgListResult msgListResult = JsonUtil.fromJson(jsonStr, MsgListResult.class);
          EventBus.getDefault().post(msgListResult);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      @Override
      public void onError(Throwable e) {
        LogUtil.i(TAG, "onOpen error: ");
      }
    });
  }

  @Override
  public void onMessage(String message) {
    LogUtil.i(TAG, "onMessage->"+message);
    sendMessageBroadcast(message);
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    LogUtil.i(TAG, "onClose->"+reason);
    mWebClient = null;
  }

  @Override
  public void onError(Exception ex) {
    LogUtil.i(TAG, "onError->"+ex.toString());
  }


  /** 初始化
   */
  public void initWebSocket(){
    new Thread(new Runnable() {
      @Override
      public void run() {
          try {
            mWebClient.connectBlocking();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
      }
    }).start();
  }

  /** 发送消息广播
   * @param message
   */
  private void sendMessageBroadcast(String message){
    if (!message.isEmpty()){
      final Msg msg = JsonUtil.fromJson(message, Msg.class);
      EventBus.getDefault().post(msg);
      LogUtil.i(TAG, "发送收到的消息: " + msg.msgContent);
    }
  }

}