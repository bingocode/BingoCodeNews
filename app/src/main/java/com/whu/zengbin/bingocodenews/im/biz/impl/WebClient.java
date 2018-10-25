package com.whu.zengbin.bingocodenews.im.biz.impl;

import android.util.Log;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import com.whu.zengbin.bingocodenews.im.bean.MsgType;
import com.whu.zengbin.bingocodenews.im.tool.IMHelper;
import org.greenrobot.eventbus.EventBus;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 创建时间: 2018/10/24 11:20 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class WebClient extends WebSocketClient{
  public static final String ACTION_RECEIVE_MESSAGE = "com.jinuo.mhwang.servermanager";
  public static final String KEY_RECEIVED_DATA = "data";
  private volatile static WebClient mWebClient;
  /**
   *  路径为ws+服务器地址+服务器端设置的子路径+参数
   *  如果服务器端为https的，则前缀的ws则变为wss
   */
  private static final String mAddress = "ws://10.33.138.61:8080/websocket";
  private void showLog(String msg){
    Log.d("WebClient---->", msg);
  }
  private WebClient(URI serverUri){
    super(serverUri, new Draft_6455());
    showLog("WebClient");
  }

  public static WebClient getInstance() {
    if (mWebClient == null) {
      synchronized (WebClient.class) {
        if (mWebClient == null) {
          try {
            mWebClient = new WebClient(new URI(mAddress));
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
    showLog("open->"+handshakedata.toString());
  }

  @Override
  public void onMessage(String message) {
    showLog("onMessage->"+message);
    sendMessageBroadcast(message);
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    showLog("onClose->"+reason);
    mWebClient = null;
  }

  @Override
  public void onError(Exception ex) {
    showLog("onError->"+ex.toString());
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
      final Msg msg = new Msg();
      msg.msgFrom = IMHelper.IS_ME;
      msg.msgType = MsgType.MSG_TEXT;
      msg.msgContent = message;
      EventBus.getDefault().post(msg);
      showLog("发送收到的消息: " + message);
    }
  }

}