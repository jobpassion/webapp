package com.redrum.webapp.service;

import java.lang.reflect.Member;
import java.sql.SQLException;
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

@Service
@RemotingDestination
public class ChatService {

	@Autowired
	private SqlMapClient sqlMapClient;
	
	public List<ChatMessageEntity> queryNew(String user) throws SQLException{
		Map m = new HashMap();
		m.put("user", user);
		List l = sqlMapClient.queryForList("chat.queryNew", m);
		sqlMapClient.update("chat.old", user);
		return l;
	}
	
	public void send(String from, String to, String message) throws SQLException{
		ChatMessageEntity cme = new ChatMessageEntity();
		cme.setFromId(from);
		cme.setToId(to);
		cme.setMessage(message);
		cme.setStatus("new");
		sqlMapClient.insert("chat.send", cme);
	}

}
