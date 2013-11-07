package com.redrum.webapp.weibo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeiboService {
	

	@PersistenceContext
	private EntityManager em;

	public List queryMsgs() {
		// TODO Auto-generated method stub
		return em.createQuery("from WeiboMsg where sendDate is null and (immediately is null or immediately = false )").getResultList();
	}

	@Transactional
	public void delete(BasicEntity o) {
		Object o2 = null;
		try {
			o2 = em.find(Class.forName(o.getClazz()), o.getId());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    em.remove(o2);
	}
	@Transactional
	public void save(BasicEntity o) {
		// TODO Auto-generated method stub
		Object o2 = null;
		try {
			o2 = em.find(Class.forName(o.getClazz()), o.getId());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//			BeanUtils.copyProperties(o2, o);
//			org.springframework.beans.BeanUtils.copyProperties(o, o2);
		try {
			Util.copy(o2, o);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		em.merge(o2);
	}
	@Transactional
	public List getCurrentSends() {
		// TODO Auto-generated method stub
		List result = em.createQuery("from WeiboMsg where immediately = true and sendDate is null", WeiboMsg.class).getResultList();
		return result;
	}
	
	public WeiboMsg getNextSend(){
	    List<WeiboMsg> result = em.createQuery("from WeiboMsg where sendDate is null order by id asc", WeiboMsg.class).getResultList();
	    if(result.size() > 0){
	        return result.get(0);
	    }
	    return null;
	}

}
