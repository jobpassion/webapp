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
	
	public void addNote(Note note){
		try {
			sqlMapClient.insert("note.insert-note", note);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void saveNote(Note note){
		try {
			sqlMapClient.insert("note.update-note", note);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
