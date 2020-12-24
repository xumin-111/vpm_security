package com.ruoyi.system.websocket;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * websocket服务端
 *
 * @author dumpling
 * @date 2020/9/14 19:33
 */
@ServerEndpoint("/websocket/changePassword")
@Component
public class WebSocketServer {


    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private static CopyOnWriteArraySet<WebSocketServer> us = new CopyOnWriteArraySet<WebSocketServer>();


    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session javax.websocket.Session
     * @param sid     sid
     * @author dumpling
     * @date 2020/9/14 19:01
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);
        System.out.println("连接成功......................"+session);
    }

    /**
     * 连接关闭调用的方法
     *
     * @author dumpling
     * @date 2020/9/14 19:03
     */
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("连接关闭...............");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 传过来的消息
     * @param session session
     * @author dumpling
     * @date 2020/9/14 19:10
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        if (StringUtils.isNotBlank(message)) {
            System.out.println("message======================="+message);
        }
    }


    /**
     * 连接异常
     *
     * @param session session
     * @param error   异常
     * @author dumpling
     * @date 2020/9/14 19:28
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     *
     * @param message 消息
     * @author dumpling
     * @date 2020/9/14 19:28
     */
    public void sendMessage(String message) throws Exception {
        this.session.getBasicRemote().sendText(message);
    }


    //群发自定义消息
    public static void sendInfo(String message)
            throws IOException {
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                item.sendMessage(message);
            } catch (Exception e) {
                continue;
            }
        }
    }
    /**
     * 初始化在线人数
     *
     * @return int 默认为0
     * @author fuxintong
     * @date 2020/9/14 19:29
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线人数加一
     *
     * @author fuxintong
     * @date 2020/9/14 19:30
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 在线人数减1
     *
     * @author fuxintong
     * @date 2020/9/14 19:30
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
