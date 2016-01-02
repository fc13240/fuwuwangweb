package com.platform.listener;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class MySessionContext {
    private static MySessionContext instance;
    private HashMap<String,HttpSession> sessionMap;
 
    private MySessionContext() {
    	sessionMap = new HashMap<String,HttpSession>();
    }

    public static MySessionContext getInstance() {
        if (instance == null) {
            instance = new MySessionContext();
        }
        return instance;
    }

    public synchronized void AddSession(HttpSession session) {
        if (session != null) {
        	sessionMap.put(session.getId(), session);
        }
    }

    public synchronized void DelSession(HttpSession session) {
        if (session != null) {
        	sessionMap.remove(session.getId());
            if(session.getAttribute("userid")!=null){
            	sessionMap.remove(session.getAttribute("userid").toString());
            	//session.invalidate(); 
            }
        }
    }

    public synchronized HttpSession getSession(String session_id) {
        if (session_id == null) return null;
        return (HttpSession) sessionMap.get(session_id);
    }

	public HashMap getSessionMap() {
		return sessionMap;
	}

	public void setMymap(HashMap sessionMap) {
		this.sessionMap = sessionMap;
	}

}