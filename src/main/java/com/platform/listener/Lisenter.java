package com.platform.listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class Lisenter implements HttpSessionAttributeListener {
	 public static Map<String, HttpSession> sessionMap=new ConcurrentHashMap<String, HttpSession>();
	 
	
	 public void attributeAdded(HttpSessionBindingEvent arg0) {
	  String name=(String)arg0.getValue();
	  if("name".equals(name)){//name���Ա����û���¼��Ϣ��name=ΪΨһ��Ϣ���û���
	   
	   if(sessionMap.containsKey(name)){//�ߵ�ǰһ�ε�¼
		    HttpSession session=sessionMap.remove(name);
		    session.invalidate();
	   }
	   sessionMap.put(name, arg0.getSession());
	  }
	 }
	 public void attributeRemoved(HttpSessionBindingEvent arg0) {}
	 public void attributeReplaced(HttpSessionBindingEvent arg0) {}
	 
	}


class MySessionListener implements HttpSessionListener {
	  public void sessionCreated(HttpSessionEvent arg0) {}
	  public void sessionDestroyed(HttpSessionEvent arg0) {
	   String name=(String)arg0.getSession().getAttribute("name");
	   if(name!=null && name.length()>0){//sessionʧЧʱ���Ƴ���¼
	    if(Lisenter.sessionMap.containsKey(name))
	    	Lisenter.sessionMap.remove(name);
	   }
	 else System.out.println("ע���û���δ��ȡ����¼�û�����");
	  }
	}
	