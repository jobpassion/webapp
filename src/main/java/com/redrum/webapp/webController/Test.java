package com.redrum.webapp.webController;
import java.util.HashMap;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingExclude;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

@Service
public class Test {

	public String test(){
		System.out.println("hello world");
		return "hello";
	}
	
	public static void main(String[] args) throws OgnlException {
		Map m = new HashMap();
		m.put("a", 1);
		System.out.println(Ognl.getValue("a", m));
	}
}