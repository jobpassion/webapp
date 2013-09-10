package com.redrum.webapp.service;

import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ognl.ClassResolver;
import ognl.DefaultClassResolver;
import ognl.DefaultTypeConverter;
import ognl.MemberAccess;
import ognl.Node;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import ognl.TypeConverter;
import ognl.enhance.ExpressionAccessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.redrum.webapp.entity.ChatMessageEntity;

import flex.messaging.FlexContext;
import flex.messaging.client.FlexClient;
import flex.messaging.client.FlexClientManager;
import flex.messaging.config.FlexClientSettings;

@Service
@RemotingDestination
public class ChatService {
	private Map cache = new HashMap();

	@Autowired
	private SqlMapClient sqlMapClient;
	
	public List queryNew(String user, String to) throws SQLException{
		cache.put(user, System.currentTimeMillis());
		Map m = new HashMap();
		m.put("user", user);
		List l = sqlMapClient.queryForList("chat.queryNew", m);
		if(null == l){
			l = new ArrayList();
		}
//		sqlMapClient.update("chat.old", user);
		FlexClient flexCliet = FlexContext.getFlexClient();
		Map status = new HashMap();
		if(null != cache.get(to)){
			
		long diff = System.currentTimeMillis() - (Long)(cache.get(to));
		status.put("diff", diff);
		}
		l.add(0, status);
		return l;
	}
	
	public void setRead(List ids){
		Map m = new HashMap();
		m.put("ids", ids);
		try {
			sqlMapClient.update("chat.setRead", m);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Integer send(String from, String to, String message) {
		Integer id = null;
		ChatMessageEntity cme = new ChatMessageEntity();
		cme.setFromId(from);
		cme.setToId(to);
		cme.setMessage(message);
		cme.setStatus("new");
		try {
			id = (Integer) sqlMapClient.insert("chat.send", cme);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
	public List queryModuleInfos(){
		List l = new ArrayList();
		try {
			l = sqlMapClient.queryForList("chat.queryModuleInfos");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
		
	}

}
