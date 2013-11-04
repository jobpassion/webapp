package com.redrum.webapp.weibo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

@Service
public class WeiboService {
	

	@PersistenceContext
	private EntityManager em;

	public List queryMsgs() {
		// TODO Auto-generated method stub
		return em.createQuery("from WeiboMsg").getResultList();
	}

}
