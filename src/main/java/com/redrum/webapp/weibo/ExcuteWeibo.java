package com.redrum.webapp.weibo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
	public void sendWeibo(WeiboMsg wm){
		initHttpClient.resetMethod();
		initHttpClient.getPostMethod().setPath("/aj/mblog/add");
		initHttpClient.getPostMethod().setQueryString("_wv=5&__rnd=" + System.currentTimeMillis());
//		initHttpClient.getPostMethod().addParameter("extra", "");
//		initHttpClient.getPostMethod().addParameter("oid", "2093492691");
//		initHttpClient.getPostMethod().addParameter("nogroup", "false");
//		initHttpClient.getPostMethod().addParameter("_t", "0");
//		initHttpClient.getPostMethod().addParameter("module", "topquick");
//		initHttpClient.getPostMethod().setRequestBody(new NameValuePair[]{
//				new NameValuePair("_t", "0"),
//				new NameValuePair("module", "topquick"),
//				new NameValuePair("location", ""),
//				new NameValuePair("_surl", ""),
//				new NameValuePair("rankid", "0"),
//				new NameValuePair("pic_id", ""),
//				new NameValuePair("text", "中文")
//		});
		String s = wm.getTitle() + " " + wm.getContent();
		int cl = 130;
		int ul = wm.getUrl().length();
		ul = ul - 140;
		if(ul > 0){
			cl = cl - new Double(Math.ceil(0.5 * ul)).intValue();
		}
		if(s.length() > cl){
			s = s.substring(0, cl - 4) + "...";
		}
//			initHttpClient.getPostMethod().addParameter("text", new String("中文".getBytes(), "UTF-8"));
//		initHttpClient.getPostMethod().addParameter("location", "");
//		initHttpClient.getPostMethod().addParameter("_surl", "");
//		initHttpClient.getPostMethod().addParameter("rankid", "0");
//		initHttpClient.getPostMethod().addParameter("pic_id", "");
		try {
			initHttpClient.getPostMethod().setRequestEntity(new StringRequestEntity("text=" + URLEncoder.encode(s + "\n" + wm.getUrl()) + "&pic_id=&rank=0&rankid=&_surl=&location=&module=topquick&_t=0", PostMethod.FORM_URL_ENCODED_CONTENT_TYPE, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			initHttpClient.getHttpClient().executeMethod(initHttpClient.getPostMethod());
			System.out.println(initHttpClient.getPostMethod().getStatusCode());
			System.out.println(initHttpClient.getPostMethod().getResponseBodyAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Integer timeCount = 0;
	
	@Autowired
	private WeiboService weiboService;
	@Scheduled(cron="0/20 * * * * *")
	public void runSend(){
	    timeCount += 20;
		List<WeiboMsg> list = weiboService.getCurrentSends();
		for(WeiboMsg wm:list){
			try{
				wm.setSendDate(new Date());
				sendWeibo(wm);
				weiboService.save(wm);
				break;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(timeCount >= 1 * 60 * 60){
		    if(list.size() == 0){
		    WeiboMsg wm = weiboService.getNextSend();
		    if(null != wm){
			try{
				wm.setSendDate(new Date());
				sendWeibo(wm);
				weiboService.save(wm);
			}catch(Exception e){
				e.printStackTrace();
			}
		    }
		    }
		    timeCount = 0;
		}
		
	}

	public static void main(String[] args) throws Exception {
		ExcuteWeibo exe = new ExcuteWeibo();
		exe.initHttpClient = new InitHttpClient();
		exe.initHttpClient.afterPropertiesSet();
//		exe.batchFollowed("1005051570845453");
//		exe.follow("1195230310");
		WeiboMsg wm = new WeiboMsg();
		wm.setContent("jjjjjjjjjjjjjjjjjj");
		exe.sendWeibo(wm);
	}
}
