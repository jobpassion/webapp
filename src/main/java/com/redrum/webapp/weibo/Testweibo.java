package com.redrum.webapp.weibo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import net.sourceforge.htmlunit.corejs.javascript.NativeObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;


import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;

public class Testweibo {

	public static void main2(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
	    final WebClient webClient = new WebClient();
	    HtmlPage page = webClient.getPage("http://weibo.com/");
//	    Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
	    System.out.println(page.getTitleText());
	    DomElement e = page.getElementByName("username");
	    e.setNodeValue("18260402168@139.com");
	    System.out.println(e);
	    e = page.getElementByName("password");
	    e.setNodeValue("261103692");
	    System.out.println(e);
//	    List l = page.getByXPath("//span[@node-type='submitStates']");
//	    e = (DomElement) l.get(4);
	    e = page.getFirstByXPath("//span[@node-type='submitStates']");
	    HtmlAnchor dn = (HtmlAnchor) e.getParentNode();
	    page = dn.click();
	    System.out.println(e);
	    System.out.println(webClient.getWebWindows().size());
	    

//	    final String pageAsXml = page.asXml();
//	    Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

//	    final String pageAsText = page.asText();
//	    Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));

	    webClient.closeAllWindows();
	}
	public static void main3(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		final WebClient webClient = new WebClient();
	    HtmlPage page = webClient.getPage("http://www.baidu.com/");
	    InputStream is = Testweibo.class.getClassLoader().getResourceAsStream("weibo.js");
	    String script = IOUtils.toString(is);
	    is.close();
	    ScriptResult sr = page.executeJavaScript(script);
	    script = "sinaSSOController.rsaPubkey = 'EB2A38568661887FA180BDDB5CABD5F21C7BFD59C090CB2D245A87AC253062882729293E5506350508E7F9AA3BB77F4333231490F915F6D63C55FE2F08A49B353F444AD3993CACC02DB784ABBB8E42A9B1BBFFFB38BE18D78E87A0E41B9B8F73A928EE0CCEE1F6739884B9777E4FE9E88A1BBE495927AC4A799B3181D6442443';sinaSSOController.servertime = '1387711657';sinaSSOController.nonce = 'GMZNTQ';sinaSSOController.rsakv = '1330428213';sinaSSOController.login('18001592606@139.com','261103692',7);sinaSSOController.from = 'weibo';sinaSSOController.useTicket = 1;";
	    sr = page.executeJavaScript(script);
	    script = "sinaSSOController.login('18001592606@139.com','261103692',7);";
	    sr = page.executeJavaScript(script);
	    NativeObject no = (NativeObject) sr.getJavaScriptResult();
	    
	    for(Object s:no.getAllIds()){
	    	System.out.println(s + "=>" + no.get(s));
	    }
	    webClient.closeAllWindows();
	}
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		httpClient.getParams().setBooleanParameter(HttpMethodParams.SINGLE_COOKIE_HEADER, true);
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		GetMethod method = new GetMethod("http://login.sina.com.cn/sso/prelogin.php?entry=weibo&callback=sinaSSOController.preloginCallBack&su=&rsakt=mod&client=ssologin.js(v1.4.11)&_=1387711657507");
		httpClient.executeMethod(method);
		String s = method.getResponseBodyAsString();
		int idx;
		idx = s.indexOf("servertime\":") + "servertime\":".length();
		String servertime = s.substring(idx, s.indexOf(",", idx));
		idx = s.indexOf("nonce\":\"") + "nonce\":\"".length();
		String nonce = s.substring(idx,s.indexOf("\"",idx));
		idx = s.indexOf("pubkey\":\"") + "pubkey\":\"".length();
		String pubkey = s.substring(idx,s.indexOf("\"",idx));
		idx = s.indexOf("rsakv\":\"") + "rsakv\":\"".length();
		String rsakv = s.substring(idx,s.indexOf("\"",idx));
		PostMethod post = new PostMethod("http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.11)");
		
		
		
		final WebClient webClient = new WebClient();
	    HtmlPage page = webClient.getPage("http://www.baidu.com/");
	    InputStream is = Testweibo.class.getClassLoader().getResourceAsStream("weibo.js");
	    String script = IOUtils.toString(is);
	    is.close();
	    ScriptResult sr = page.executeJavaScript(script);
	    script = "sinaSSOController.rsaPubkey = '" + pubkey + "';sinaSSOController.servertime = '" + servertime + "';sinaSSOController.nonce = '" + nonce + "';sinaSSOController.rsakv = '" + rsakv + "';sinaSSOController.from = 'weibo';sinaSSOController.useTicket = 1;";
	    sr = page.executeJavaScript(script);
	    script = "sinaSSOController.login('18260402168@139.com','261103692',7);";
	    sr = page.executeJavaScript(script);
	    NativeObject no = (NativeObject) sr.getJavaScriptResult();
	    
	    for(Object o:no.getAllIds()){
//	    	System.out.println(o + "=>" + no.get(o));
	    	post.setParameter(o + "", no.get(o) + "");
	    }
	    webClient.closeAllWindows();
	    post.setParameter("gateway", "1");
	    post.setParameter("savestate", "7");
	    post.setParameter("useticket", "1");
	    post.setParameter("pagerefer", "http://login.sina.com.cn/sso/logout.php?entry=miniblog&r=http%3A%2F%2Fweibo.com%2Flogout.php%3Fbackurl%3D%252F");
	    post.setParameter("vsnf", "1");
	    post.setParameter("encoding", "UTF-8");
	    post.setParameter("prelt", "140");
	    post.setParameter("url", "http://weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack");
	    post.setParameter("returntype", "META");
	    httpClient.executeMethod(post);
	    s = new String(post.getResponseBody(),"UTF-8");
	    idx = s.indexOf("location.replace(\"") + "location.replace(\"".length();
	    String url = s.substring(idx,s.indexOf("\"",idx));
	    System.out.println(url);
	    method = new GetMethod(url);
	    httpClient.executeMethod(method);
	    System.out.println(method.getResponseBodyAsString());
	    method.releaseConnection();
	    method = new GetMethod("http://weibo.com/");
	    httpClient.executeMethod(method);
	    System.out.println(new String(method.getResponseBody(),"UTF-8"));
	}
}
