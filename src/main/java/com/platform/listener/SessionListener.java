package com.platform.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionListener implements HttpSessionListener {
    public  static MySessionContext sessionContext=MySessionContext.getInstance();
 
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    	sessionContext.AddSession(httpSessionEvent.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        sessionContext.DelSession(httpSessionEvent.getSession());
    }
}