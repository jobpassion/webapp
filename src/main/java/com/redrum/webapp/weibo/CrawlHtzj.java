package com.redrum.webapp.weibo;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weibo4j.ShortUrl;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

//@Service
public class CrawlHtzj {
	
	
//	<th class="(common|new)">\s*\n.*<a href="([^"]*)" onclick=.*>(.*)</a>
//	<a href="([^"]+)".+Title=([^\n]+)'\][^\n]+\n[^\n]+\n[^\n]+\n[^\n]+\n[^\n]+\n[^\n]+\n[^\n]+商城：[^\n]*\n.*(亚马逊中国)[^\n]+\n[^\n]+\n[^\n]+\n([\s\S]+?)<div
//	group1:url
//	group2:title
//	group3:商城
//	private String regexp = "<a href=\"([^\"]*)\".*(?=Title)Title=(.*)(?='\\]).*\\r\\n.*\\r\\n.*\\r\\n.*\\r\\n.*\\r\\n.*商城：(亚马逊中国)</div>";
	private String[] regexp = new String[]{"<th class=\"(common|new)\">\\s*\\n.*<a href=\"([^\"]*)\" onclick=.*>(.*)</a>"
	};
	private String[] url = new String[]{"http://www.haitaozj.com/forum-38-1.html"
	};
	private String[] storeType = new String[]{"美国亚马逊"};
	private HttpClient httpClient;
	private GetMethod getMethod;
	
	@Value("${access_token}")
	private String access_token ="2.00Lj6eJEiAwHIBdb114dc228iRIPyD";
	
	@Autowired
	private InitHttpClient initHttpClient;
	@PersistenceContext
	private EntityManager em;
	private Logger logger = Logger.getLogger(CrawlHtzj.class);
	private HttpURLConnection getGetMethod(String url){
		try {
			URL u = new URL(url);
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			return c;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	@PostConstruct
	public void init(){
		httpClient = new HttpClient();
//		try {
////			httpClient.startSession(new URL(url));
////	        HostConfiguration conf = new HostConfiguration();
////	        conf.setHost(new URI(url));
////	        httpClient.setHostConfiguration(conf);
//		}catch (URIException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		getMethod = new GetMethod();
	}
	
	public String trans(String sUrl){
//		try {
//			httpClient.getHostConfiguration().setHost(new URI(sUrl));
//		} catch (URIException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		HttpURLConnection c = getGetMethod(sUrl);
//		getMethod.recycle();
//		getMethod.setPath(sUrl.substring(sUrl.indexOf("smzdm.com") + "smzdm.com".length()));
		try {
//			httpClient.executeMethod(getMethod);
//			String response = getMethod.getResponseBodyAsString();
			c.connect();
			String response = IOUtils.toString(c.getInputStream());
			c.disconnect();
			int i1 = response.indexOf("http://www.amazon");
			if(i1<0)
				return "";
			String tUrl = response.substring(i1, response.indexOf("'", i1));
			tUrl = tUrl.replaceAll("joyo01y-23", "weibo0f0-23");
			tUrl = tUrl.replaceAll("joyo01y-20", "weibo02a-20");
			tUrl = tUrl.replaceAll("creative=3132", "creative=3200");

//			sUrl = sUrl.replaceAll("[\u4e00-\u9fa5]+", "");

			
			return tUrl;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sUrl;
	}
	public String shortUrl(String url){

		ShortUrl su = new ShortUrl();
		su.client.setToken(access_token);
		try {
			JSONObject jo = su.longToShortUrl(url);
			logger.debug(jo.toString());
			url = ((JSONObject)(jo.getJSONArray("urls").get(0))).getString("url_short");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	@Scheduled(cron="10 * * * * *")
	@Transactional
	public void excute(){
		for(int i=0; i<regexp.length; i++){
//		getMethod.recycle();
//		getMethod.setPath("/page/1");
//		try {
//			httpClient.executeMethod(getMethod);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String response = null;
//		try {
//			response = getMethod.getResponseBodyAsString();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		HttpURLConnection c = getGetMethod(url[i]);
//		try {
//			c.connect();
//			response = IOUtils.toString(c.getInputStream());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			continue;
//		}
//		c.disconnect();
		getMethod = new GetMethod(url[i]);
		try {
			httpClient.executeMethod(getMethod);
			getMethod.releaseConnection();
			httpClient.executeMethod(getMethod);
			response = new String(getMethod.getResponseBody(), "gbk");
		} catch (HttpException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response = response.replaceAll("什么值得买", "海淘");
		response = response.replaceAll("&nbsp;", " ");
		Pattern pattern = Pattern.compile(regexp[i]);
		Matcher matcher = pattern.matcher(response);
		try {
			FileUtils.write(new File("a.html"), response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(0==i){
			logger.info("craw haitaozhijia start");
		}
		while(matcher.find()){
			String url = matcher.group(1);
			String identi = url.substring(url.lastIndexOf("/") + 1);
			String storeType = matcher.group(3);
			url = trans(url);
			logger.debug(url);
			String title = matcher.group(2);
			String content = matcher.group(4);
			if(null != em.find(SmzdmIdentifi.class, identi))
				continue;
			else{
				SmzdmIdentifi smz = new SmzdmIdentifi();
				smz.setId(identi);
				em.persist(smz);
			}
			logger.info(title);
			/*content = content.replaceAll("<a[^>]*href=\"([^\"]*)\"[^>]*>", "[[$1]]");
			content = content.replaceAll("<[^>]*>", "");
			content = content.replaceAll("\\s*", "");

			Pattern pattern2 = Pattern.compile("\\[\\[([^]]*)\\]\\]");
			Matcher matcher2= pattern2.matcher(content);
			while(matcher2.find()){
//				String s = matcher2.group(1);
//				String s2 = trans(s);
//				if(s2.isEmpty())
//					content = content.replaceAll(matcher2.group(0), "");
//				else
//					content = content.replaceAll(s, s2);
				
				content = content.replaceAll(matcher2.group(0), "");
			}
			content = content.replaceAll("\\[\\[\\]\\]", "");*/
			WeiboMsg wmsg = new WeiboMsg();
			wmsg.setSouceContent(content);
			Pattern imgPattern = Pattern.compile("data-original=\"([^\"]+)\"");
			Matcher imgMatcher = imgPattern.matcher(content);
			String imgUrl = null;
			while(imgMatcher.find()){
				imgUrl = imgMatcher.group(1);
				break;
			}
			
			content = content.replaceAll("<[^>]+>", "");
			content = content.replaceAll("\\s+", "");
			
			logger.debug(content);
			wmsg.setContent(content);
			content = wmsg.getSouceContent();
			content = content.replaceAll("<p[^>]+>", "[换行]");
			content = content.replaceAll("<[^>]+>", "");
			content = content.replaceAll("\\s+", "");
			content = content.replaceAll("\\[换行\\]", "\n");
			content = content+"\n[url=" + url + "][size=6]点击进入[/size][/url]";
			wmsg.setDzContent(content);
			wmsg.setCreateDate(new Date());
			wmsg.setLastModifyDate(new Date());
			wmsg.setSource("smzdm");
			wmsg.setTitle(title);
			wmsg.setUrl(url);
			wmsg.setImgUrl(imgUrl);
//			wmsg.setShotUrl(shortUrl(url));
			wmsg.setStoreType(storeType);
//			try{
//			URL url2 = new URL("http://www.dwz.cn/create.php");
//			HttpURLConnection c2 = (HttpURLConnection) url2.openConnection();
//			c2.setRequestMethod("POST");
//			c2.setDoOutput(true);
//			IOUtils.write("access_type=web&url=" + wmsg.getUrl(), c2.getOutputStream(), "UTF-8");
//			String s = IOUtils.toString(c2.getInputStream());
//			s = s.substring(s.indexOf("http"), s.indexOf("\",\""));
//			s = s.replaceAll("\\\\/", "/");
//			wmsg.setShotUrl(s);
//			c2.disconnect();
//			}catch(Exception e){}
			em.merge(wmsg);
		}
		}
	}
	
	public static void main(String[] args) throws Exception {
		CrawlHtzj craw = new CrawlHtzj();
		craw.init();
		craw.excute();
	}

}
