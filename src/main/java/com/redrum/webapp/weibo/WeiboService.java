package com.redrum.webapp.weibo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redrum.webapp.weibo.entity.FansEntity;
import com.redrum.webapp.weibo.entity.TimeRangeEntity;
import com.redrum.webapp.weibo.entity.WeiboAccount;

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
		Object o2 = o;
		if(null !=o.getId()){
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
		}}
		em.merge(o2);
	}
	@Transactional
	public List getCurrentSends() {
		// TODO Auto-generated method stub
		List result = em.createQuery("from WeiboMsg where sendDate is null", WeiboMsg.class).getResultList();
		return result;
	}
	
	public WeiboMsg getNextSend(){
	    List<WeiboMsg> result = em.createQuery("from WeiboMsg where sendDate is null order by id asc", WeiboMsg.class).getResultList();
	    if(result.size() > 0){
	        return result.get(0);
	    }
	    return null;
	}

	public List<TimeRangeEntity> getRanges() {
		// TODO Auto-generated method stub
		List result = em.createQuery("from TimeRangeEntity", TimeRangeEntity.class).getResultList();
		return result;
	}

	public boolean existFan(String id) {
		// TODO Auto-generated method stub
		TypedQuery<FansEntity> query = em.createQuery("from FansEntity where userId = :userId", FansEntity.class);
		query.setParameter("userId", id);
		if(query.getResultList().size() > 0)
			return true;
		return false;
		
	}

	public List<FansEntity> getNextFansToFollow() {
		// TODO Auto-generated method stub
		TypedQuery<FansEntity> query = em.createQuery("from FansEntity where following = :following order by id asc", FansEntity.class);
		query.setParameter("following", false);
		query.setMaxResults(1);
		return query.getResultList();
	}
	public List<FansEntity> getNextFansToFollowL() {
		// TODO Auto-generated method stub
		TypedQuery<FansEntity> query = em.createQuery("from FansEntity where following = :following order by id desc", FansEntity.class);
		query.setParameter("following", false);
		query.setMaxResults(1);
		return query.getResultList();
	}
	
	public List<WeiboAccount> queryAccounts(){

		return em.createQuery("from WeiboAccount where enable = 1").getResultList();
	}


}
