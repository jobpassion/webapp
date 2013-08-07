package com.redrum.webapp.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redrum.webapp.dao.DaoHelper;
public class TestService {
	private DaoHelper daoHelper;
	
	public void setDaoHelper(DaoHelper daoHelper) {
		this.daoHelper = daoHelper;
	}
	
	class TestJob implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

//			System.out.println(daoHelper.getSqlMapClientTemplate().
//					queryForList("test1", new Object()));
			try {
				daoHelper.getSqlMapClient().queryForList("T.findAllStudent");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void init(){
		
		TestJob t = new TestJob();
		Thread th = new Thread(t);
		th.start();
	}

}
