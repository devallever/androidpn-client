/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.client;

import android.util.Log;

/** 
 * A thread class for recennecting the server.
 * 断线重连的线程类
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class ReconnectionThread extends Thread {

    private static final String LOGTAG = LogUtil
            .makeLogTag(ReconnectionThread.class);

    private final XmppManager xmppManager;

    //重连次数
    private int waiting;

    ReconnectionThread(XmppManager xmppManager) {
        this.xmppManager = xmppManager;
        this.waiting = 0;
    }

    public void run() {
        try {
            //当前线程不被中断，并且没有进行身份认证
            while (!xmppManager.getConnection().isAuthenticated() && !isInterrupted()) {
                Log.d(LOGTAG, "Trying to reconnect in " + waiting()
                        + " seconds");
                Thread.sleep((long) waiting() * 1000L);
                xmppManager.connect();
                waiting++;
            }
        } catch (final InterruptedException e) {
            xmppManager.getHandler().post(new Runnable() {
                public void run() {
                    xmppManager.getConnectionListener().reconnectionFailed(e);
                }
            });
        }
    }

    private int waiting() {
        //减少耗电量，前期重连次数比较频繁，后期如果连不上则延长重连时间

        //如果重连次数大于20秒，则10分钟一次
        if (waiting > 20) {
            return 600;
        }

        //如果重连次数大于13次小于20次，则5分钟重连一次
        if (waiting > 13) {
            return 300;
        }

        //如果重连次数小于7次，则10秒重连一次
        //如果重连次数大于7次小于13，则60秒重连一次
        return waiting <= 7 ? 10 : 60;
    }
}
