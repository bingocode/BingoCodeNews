package com.whu.zengbin.bingocodenews.im.biz.impl;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.whu.zengbin.bingocodenews.CodeNewsApp;
import com.whu.zengbin.bingocodenews.common.*;
import com.whu.zengbin.bingocodenews.im.bean.Msg;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import org.greenrobot.eventbus.EventBus;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 创建时间: 2018/10/24 11:20 <br>
 * 作者: zengbin <br>
 * 描述: websocket客户端类
 */
public class WebClient extends WebSocketClient{
  private static final String TAG = "WebClient";
  public static final String ACTION_RECEIVE_MESSAGE = "com.jinuo.mhwang.servermanager";
  public static final String KEY_RECEIVED_DATA = "data";
  private volatile static WebClient mWebClient;
  private boolean isFinish = false;
  private Runnable mHeartBeatRunnable;
  private Runnable mHeartBeatCheckRunnable;
  private int mHeartBeatFailedCount = 0;
  private long mHeartBeatLastTime = 0;
  protected Handler mMainHandler;

  /**
   * 心跳发送失败最大次数
   */
  private final static int MAX_HEARTBEAT_FAILED_COUNT = 7;
  /**
   * 心跳接收最大间隔
   */
  private final static int MAX_HEARTBEAT_RECEIVE_INTERVAL = 10 * 1000;
  /**
   * 心跳发送间隔
   */
  long TIMER_HEARTBEAT_INTERVAL = 2 * 1000;

  /**
   *  路径为ws+服务器地址+服务器端设置的子路径+参数
   *  如果服务器端为https的，则前缀的ws则变为wss
   */
  private static final String mAddress = "ws://"+ ConstraintUtil.BASE_IP+":8080/websocket/";

  private WebClient(URI serverUri){
    super(serverUri, new Draft_6455());
    LogUtil.i(TAG, "new WebClient");
    mHeartBeatRunnable = new Runnable() {
      @Override public void run() {
        if (isFinish) {
          return;
        }
        sendHeartBeatCmd();
      }
    };
    mHeartBeatCheckRunnable = new Runnable() {
      @Override public void run() {
        if (isFinish) {
          return;
        }
        long interval = System.currentTimeMillis() - mHeartBeatLastTime;
        if (interval >= MAX_HEARTBEAT_RECEIVE_INTERVAL) {
          LogUtil.e(TAG, "has too long time not receive heartbeat, interval = " + interval);
          close();
        } else {
          mMainHandler.postDelayed(mHeartBeatCheckRunnable, TIMER_HEARTBEAT_INTERVAL);
        }
      }
    };
    mMainHandler = new Handler(Looper.getMainLooper());

  }

  private void sendHeartBeatCmd() {

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
    isFinish = false;
    //TODO 参考语音通话的心跳机制进行处理
    mMainHandler.postDelayed(mHeartBeatCheckRunnable, TIMER_HEARTBEAT_INTERVAL);
    mHeartBeatLastTime = System.currentTimeMillis();

    sendPing();
  }

  @Override
  public void onMessage(String message) {
    LogUtil.i(TAG, "onMessage->"+message);
    receiveMessage(message);
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    LogUtil.i(TAG, "onClose->"+reason);
    isFinish = true;
    mWebClient = null;
  }

  @Override
  public void onError(Exception ex) {
    LogUtil.i(TAG, "onError->"+ex.toString());
  }

  @Override
  public void onWebsocketPing(WebSocket conn, Framedata f) {
    super.onWebsocketPing(conn, f);
    LogUtil.i(TAG, "onWebsocketPing");
  }

  @Override
  public void onWebsocketPong(WebSocket conn, Framedata f) {
    super.onWebsocketPong(conn, f);
    LogUtil.i(TAG, "onWebsocketPong");
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

  /** 收到消息
   * @param message
   */
  private void receiveMessage(String message){
    if (!message.isEmpty()){
      final Msg msg = JsonUtil.fromJson(message, Msg.class);
      EventBus.getDefault().post(msg);
      LogUtil.i(TAG, "发送收到的消息: " + msg.msgContent);
    }
  }

  public void sendMessage(final Msg msg) {
    if(msg != null) {
      if (isOpen()) {
        send(JsonUtil.toJson(msg));
      } else {
        Observable.just(msg)
            .subscribeOn(ThreadUtil.getIMScheduler())
            .map(new Function<Msg, Boolean>() {
              @Override
              public Boolean apply(Msg msg) throws Exception {
                return reconnectBlocking();
              }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Boolean>() {
              @Override
              public void accept(Boolean isOpen) throws Exception {
                if (isOpen) {
                  send(JsonUtil.toJson(msg));
                } else {
                  ToastUtil.toastShort("连接断开，发送失败");
                }
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                ToastUtil.toastShort("连接断开，发送失败");
              }
            });
      }
    }
  }



}