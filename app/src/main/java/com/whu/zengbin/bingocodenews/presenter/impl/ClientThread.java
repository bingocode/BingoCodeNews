package com.whu.zengbin.bingocodenews.presenter.impl;

import com.whu.zengbin.bingocodenews.common.LogUtil;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 创建时间: 2018/08/07 11:55 <br>
 * 作者: zengbin <br>
 * 描述:
 */
public class ClientThread implements Runnable {
  public static final String TAG = "ClientThread";

  @Override public void run() {
    try {
      LogUtil.i(TAG,"Client：Connecting");
      //IP地址和端口号（对应服务端），我这的IP是本地路由器的IP地址
      Socket socket = new Socket("10.33.73.10", 12345);
      //发送给服务端的消息
      String message = "Message from Android phone";
      try {
        LogUtil.i(TAG,"Client Sending: '" + message + "'");

        //第二个参数为True则为自动flush
        PrintWriter out = new PrintWriter(
            new BufferedWriter(new OutputStreamWriter(
                socket.getOutputStream())), true);
        out.println(message);
        //						out.flush();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        //关闭Socket
        socket.close();
        LogUtil.i(TAG,"Client:Socket closed");
      }
    } catch (UnknownHostException e1) {
      e1.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
