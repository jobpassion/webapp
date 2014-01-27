package com.redrum.webapp.weibo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class Testweibo2 {

	public static void main(String[] args) throws HttpException, IOException {
		// TODO Auto-generated get stub

		HttpClient httpClient = new HttpClient();
		GetMethod get = new GetMethod("http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack&ticket=ST-MjM1MDc5NTI1NA==-1387888871-gz-E7D83C824D5A0895AF2087D39FA39D1E");
//		List<NameValuePair> l = new ArrayList();
//		l.add(new NameValuePair("ssosavestate", "1390480661"));
//		l.add(new NameValuePair("url", "http%3A%2F%2Fweibo.com%2Fajaxlogin.php%3Fframelogin%3D1%26callback%3Dparent.sinaSSOController.feedBackUrlCallBack&ticket=ST-MjM1MDc5NTI1NA==-1387888661-gz-0E1CFEFD5D435FD0F295CBC68FFAA9E2"));
//		l.add(new NameValuePair("retcode", "0"));
//		get.setQueryString(l.toArray(new NameValuePair[l.size()]));
//		get.setFollowRedirects(false);
		get.setHttp11(true);
	    get
				.addRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
	    get.addRequestHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
	    get.addRequestHeader("Accept-Encoding","deflate,sdch");
	    get.addRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	    get.addRequestHeader("Connection","keep-alive");
	    get.addRequestHeader("Host","weibo.com");
	    get.addRequestHeader("If-Modified-Since","Tue, 24 Dec 2013 22:22:09 GMT");
		httpClient.executeMethod(get);
//		System.out.println(get.getResponseBodyAsString());
	    System.out.println(new String(get.getResponseBody(),"UTF-8"));

	}

}
