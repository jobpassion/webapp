package com.redrum.webapp.weibo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class Test {
	public static void main(String[] args) {
		String url = "http://passport.scol.com.cn/login_save.asp";// 论坛的登陆页面
		String url2 = "http://www.scol.cn/forum.php?mod=post&action=reply&fid=1270&tid=14352441&extra=page%3D1&replysubmit=yes&infloat=yes&handlekey=fastpost";// 论坛的发贴页面

		String sessionid=null;


		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(
		HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		httpClient.getParams().setCookiePolicy(
		CookiePolicy.BROWSER_COMPATIBILITY);
		PostMethod postMethod = new PostMethod(url);

		NameValuePair[] data = {
		new NameValuePair("loginfield", "username"),
		new NameValuePair("u_name", "xx"),
		new NameValuePair("u_passwd", "ccc"),
		new NameValuePair("referer",
		"http://www.scol.cn/thread-14352441-1-1.html") };
		postMethod.setRequestHeader("Referer",
		"http://www.scol.cn/thread-14352441-1-1.html");
		postMethod.setRequestHeader("Host", "www.scol.cn");
		postMethod.setRequestHeader("Connection", "keep-alive");

		postMethod
		.setRequestHeader(
		"User-Agent",
		"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)");
		postMethod
		.setRequestHeader("Accept",
		"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		postMethod.addRequestHeader("Content-Type",
		"application/x-www-form-urlencoded;charset=utf-8");
		postMethod.setRequestBody(data);
		try {

		//第1步,登录
		//使用POST方式提交数据   
		postMethod.getParameter("SessionID");
		int statuscode = httpClient.executeMethod(postMethod);
		StringBuffer response = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
		postMethod.getResponseBodyAsStream(), "gb2312"));// 以gb2312编码方式打印从服务器端返回的请求
		String line;
		while ((line = reader.readLine()) != null) {
		response.append(line).append(
		System.getProperty("line.separator"));
		}
		reader.close();
		System.out.println("---------------------------" + response);

		String headersrt = postMethod.getResponseHeader("Set-Cookie").getValue();

		//取出登陆成功后，服务器返回的cookies信息，里面保存了服务器端给的“临时证”
		Cookie[] cookies = httpClient.getState().getCookies();
		String tmpcookies = "";
		for (Cookie c : cookies) {
		tmpcookies = tmpcookies + c.toString() + ";";
		System.out.println(c);
		}
		System.out.println(tmpcookies);
		String cookieValue = cookies[0].getValue()+";"+cookies[0].getName()+";"+cookies[0].getDomain()+";"+
		cookies[0].getVersion()+";"+cookies[0].getExpiryDate();
		for (int i = 0; i < cookies.length; i++) {
		System.out.println("cookiename==" + cookies[i].getName());
		System.out.println("cookieValue==" + cookies[i].getValue());
		System.out.println("Domain==" + cookies[i].getDomain());
		System.out.println("Path==" + cookies[i].getPath());
		System.out.println("Version==" + cookies[i].getVersion());
		System.out.println("ExpiryDate==" + cookies[i].getExpiryDate());
		}
		System.out.println("---------------------------" + response);
		postMethod.releaseConnection();

		//取formhash值
		String formhash =null; 

		//检查是否重定向
		int statuscode2 = postMethod.getStatusCode();
		if ((statuscode2 == HttpStatus.SC_MOVED_TEMPORARILY) ||
		(statuscode2 == HttpStatus.SC_MOVED_PERMANENTLY) ||
		(statuscode2 == HttpStatus.SC_SEE_OTHER) ||
		(statuscode2 == HttpStatus.SC_TEMPORARY_REDIRECT)) {
		// 读取新的URL地址
		Header header2 = postMethod.getResponseHeader("location");
		if (header2 != null) {
		String newuri = header2.getValue();
		if ((newuri == null) || (newuri.equals("")))
		newuri = "/"; 
		/*int return_len = newuri.indexOf("return=");
		String  return_url = newuri.substring(return_len+7, newuri.length());
		System.out.println("-----------------"+return_url);
		PostMethod redirect = new PostMethod("http://www.scol.cn/portal.php");
		httpClient.executeMethod(redirect);
		System.out.println("Redirect:"
		+ redirect.getStatusLine().toString());
		String response1 = redirect.getResponseBodyAsString();
		System.out.println("-------------response1----------"+response1);


		redirect.releaseConnection();*/
		} else{
		System.out.println("Invalid redirect");
		//like a 404 Not Found error. 
		}
		}

		//第2步,根据登录后的回复页面取页面formhash 值
		PostMethod getMethod = new
		PostMethod("http://www.scol.cn/thread-14352441-1-1.html");
		//每次访问需授权的网址时需带上前面的 cookie 作为通行证
		getMethod.setRequestHeader("Cookie",tmpcookies); 
		int status2 =httpClient.executeMethod(getMethod); 
		String response2 = getMethod.getResponseBodyAsString();
		System.out.println("-------------responseGet----------"+response2); 
		getMethod.releaseConnection();

		formhash = getFormhash(response2.toString());

		 if(response2.indexOf("title=\"访问我的空间\"") != -1){  
		     int pos = response2.indexOf("title=\"访问我的空间\"");  
		     String username =response2.substring(pos+15, pos+50);  
		     username = username.substring(0, username.indexOf("<", 1));  
		     System.out.println("登录时的 用户名为："+username);  
		     System.out.println("#################################   登录成功   ############################");  
		 }else{  
		 System.out.println("#################################   登录NO成功   ############################");  
		 } 

		// -------------------------------------------------------------------------------------------

		String cookiesStr="pgv_info=ssi=s5414212730;pgv_pvi=9649324596;"+
		"tVi9_9a35_auth= ed5al92Yr+XSUJJm9PMY8L8Dfenpds0ZbDlX4Sw86yE3c7Qkle2FCAyAnbNtKOjYMTZyjm70K01ypLQ51b2g+ihNoi3K;tVi9_9a35_checkpm=1;tVi9_9a35_connect_is_bind=0;tVi9_9a35_cookiereport=d18aXkEmEiff2ikQTc+I7vSjP6SX/Zx6ZP4x4Ac85/mDCeVvmQn/;"+
		"tVi9_9a35_lastact=1341386131;tVi9_9a35_lastvisit=1341382202;"+
		"tVi9_9a35_saltkey=N1NBBJgj;tVi9_9a35_sendmail=1"+
		"tVi9_9a35_sid	=ivt2IF;tVi9_9a35_smile=1D1;"+
		"tVi9_9a35_ulastactivity=5d40YB3RJv95k1OsiWiZB76w0yn2QEBaEK3Jmx/5+JGFTR8r5RMM;"+
		"tVi9_9a35_visitedfid=1270;"+
		"UP=u_lgtime=2012-7-4+15:10:26&uud=2db4ba0a0cf49a7e&u_name=xxxxx2&uvd=2012-7-11+15:10:26";

		//进行登陆后的操作
		PostMethod postMethod2 = new PostMethod(url2);
		postMethod2.setRequestHeader("Content-Type",
		"application/x-www-form-urlencoded;charset=utf-8");
		NameValuePair[] data2 = {
		new NameValuePair("message", "test123"),
		new NameValuePair("subject", ""),
		new NameValuePair("formhash", formhash)
		};
		//每次访问需授权的网址时需带上前面的 cookie 作为通行证
		postMethod2.setRequestHeader("Cookie", tmpcookies);
		postMethod.setRequestHeader("Host", "www.scol.cn");
		postMethod.setRequestHeader("Connection", "keep-alive");
		postMethod.setRequestHeader(
		"User-Agent",
		"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)");
		postMethod.setRequestHeader("Accept",
		"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		postMethod2.setRequestBody(data2);

		//第3步,执行回贴
		int status = httpClient.executeMethod(postMethod2);
		String responseGet = postMethod2.getResponseBodyAsString();
		System.out.println("-------------responseGet----------"+ responseGet);
		postMethod2.releaseConnection();

		} catch (Exception e) {
		System.out.println(e.getMessage());
		} finally {
		// postMethod.releaseConnection();
		// postMethod2.releaseConnection();
		}
	}
	
	public static String getFormhash(String response) {
		String formhash = "none";
		int posi = response.indexOf("formhash");
		if (posi != -1) {
		formhash = response.substring(posi + 9, posi + 17);
		System.out.println("获取formhash值:" + formhash);
		}
		return formhash;
		}
}
