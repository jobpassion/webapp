package com.redrum.webapp.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.redrum.webapp.dao.DaoHelper;
@Service
public class TestService  implements  InitializingBean{
	@Autowired
	private DaoHelper daoHelper;
	@Autowired
	private SqlMapClient sqlMapClient;
	

	public void init(){

//		try {
//			daoHelper.getSqlMapClient().queryForList("T.test2");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}


	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		init();
	}


	public List queryAllTest() throws SQLException {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("T.queryAllTest");
	}
	
	public static class T{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static void main(String[] args) {
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("name == 'abc'");
		T t = new T();
		t.name = "abc";
		EvaluationContext context = new StandardEvaluationContext(t);
		boolean result = exp.getValue(context, Boolean.class);  // evaluates to true
		System.out.println(result);
	}

}
