package com.redrum.webapp.chromeNote.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.MapperConfig;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.redrum.webapp.chromeNote.entity.Note;
import com.redrum.webapp.chromeNote.entity.User;
import com.redrum.webapp.dao.DaoHelper;
@Service
public class NoteService  implements  InitializingBean{
	@Autowired
	private DaoHelper daoHelper;
	@Autowired
	private SqlMapClient sqlMapClient;
	

	public void init(){
	}


	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		init();
	}


	public List queryAllTest() throws SQLException {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("T.queryAllTest");
	}
	
	public Integer addNote(Note note){
		try {
			Integer id = (Integer) sqlMapClient.insert("note.insert-note", note);
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void saveNote(Note note){
		try {
			sqlMapClient.insert("note.update-note", note);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public List<Note> queryNotesByUrl(String url, String userId) throws SQLException {
		// TODO Auto-generated method stub
		Map param = new HashMap();
		param.put("url", url);
		param.put("userId", userId);
		return sqlMapClient.queryForList("note.queryNotesByUrl", param);
	}
	public List<Note> loadTitles(String userId) throws SQLException {
		// TODO Auto-generated method stub
		Map param = new HashMap();
		param.put("userId", userId);
		return sqlMapClient.queryForList("note.loadTitles", param);
	}


	public List<Note> loadNote(String id) throws SQLException {
		// TODO Auto-generated method stub
		Map param = new HashMap();
		param.put("id", id);
		return sqlMapClient.queryForList("note.loadNote", param);
	}


	public Integer addUser(User user) {
		try {
			Integer id = (Integer) sqlMapClient.insert("note.insert-user", user);
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
