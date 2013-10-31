package com.redrum.webapp.weibo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcuteWeibo {
	@Autowired
	private InitHttpClient initHttpClient;

	public Set getFans(Set result, String uid, int page) {
		System.out.println(uid + " page:" + page);
		initHttpClient.resetMethod();
//		initHttpClient.getHttpMethod().setPath(
//				"/p/" + uid + "/follow?relate=fans&page=" + page);
		initHttpClient.getHttpMethod().setPath(
				"/p/" + uid + "/follow");
		initHttpClient.getHttpMethod().setQueryString("relate=fans&page=" + page);
		String responseString = null;
		try {
			initHttpClient.getHttpClient().executeMethod(
					initHttpClient.getHttpMethod());
			responseString = initHttpClient.getHttpMethod()
					.getResponseBodyAsString();
			// System.out.println(responseString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pattern pattern = Pattern.compile("action-data=\\\\\"uid=([^&]*)&");
		Matcher matcher = pattern.matcher(responseString);
//		System.out.println(responseString);
//		File f = new File("f_" + uid + "_" + page + ".log");
//		try {
//			f.createNewFile();
//			OutputStream os = new FileOutputStream(f);
//			IOUtils.write(responseString, os);
//			os.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		boolean end = true;
		while (matcher.find()) {
			String i = matcher.group(1);
			if (!result.contains(i)) {
				result.add(i);
				end = false;
			}
		}
		if (!end) {
			getFans(result, uid, page + 1);
		}
		return result;
	}
	
	public void follow(String uid){
		initHttpClient.resetMethod();
		initHttpClient.getPostMethod().setPath("/aj/f/followed");
		initHttpClient.getPostMethod().setQueryString("_wv=5&__rnd=1382356548595");
		initHttpClient.getPostMethod().addParameter("uid", uid);
		initHttpClient.getPostMethod().addParameter("f", "1");
//		initHttpClient.getPostMethod().addParameter("extra", "");
		initHttpClient.getPostMethod().addParameter("refer_sort", "profile");
		initHttpClient.getPostMethod().addParameter("refer_flag", "profile_head");
		initHttpClient.getPostMethod().addParameter("location", "page_100505_home");
//		initHttpClient.getPostMethod().addParameter("oid", "2093492691");
		initHttpClient.getPostMethod().addParameter("wforce", "1");
//		initHttpClient.getPostMethod().addParameter("nogroup", "false");
		initHttpClient.getPostMethod().addParameter("_t", "0");
		try {
			initHttpClient.getHttpClient().executeMethod(initHttpClient.getPostMethod());
			System.out.println(initHttpClient.getPostMethod().getStatusCode());
			System.out.println(initHttpClient.getPostMethod().getResponseBodyAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		PostMethod a;
//		a.set
	}

	public void batchFollowed(String uid) {
		Set<String> fans = getFans(new HashSet(), uid, 1);
		System.out.println("total find " + fans.size());
		for(String s:fans){
			follow(s);
		}
	}

	public static void main(String[] args) throws Exception {
		ExcuteWeibo exe = new ExcuteWeibo();
		exe.initHttpClient = new InitHttpClient();
		exe.initHttpClient.afterPropertiesSet();
		exe.batchFollowed("1005051570845453");
//		exe.follow("1195230310");
	}
}
