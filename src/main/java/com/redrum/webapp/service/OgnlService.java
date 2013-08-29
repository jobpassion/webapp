package com.redrum.webapp.service;

import java.lang.reflect.Member;
import java.util.HashMap;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

@Service
@RemotingDestination
public class OgnlService implements ApplicationContextAware {
	private WebOgnlContext ognlContext;

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		ognlContext = new WebOgnlContext();
		ognlContext.appContext = arg0;
	}
	
	public Object eval(String expr) throws OgnlException{
		System.out.println("excuted");
		return Ognl.getValue(expr, ognlContext);
	}
	
	
	class WebOgnlContext extends HashMap{
		public ApplicationContext appContext;
		@Override
		public Object get(Object beanName){
			return appContext.getBean((String) beanName);
		}
		
	}
	

}
