package com.redrum.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redrum.webapp.dao.DaoHelper;
public class TestService {
	private DaoHelper daoHelper;
	
	public void setDaoHelper(DaoHelper daoHelper) {
		this.daoHelper = daoHelper;
	}

	public void init(){
		System.out.println(daoHelper.getSqlMapClientTemplate().queryForList("test", new Object()));
	}

}
