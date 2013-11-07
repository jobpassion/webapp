package com.redrum.webapp.weibo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Crawl3rdParty {
	
	
//	<a href="([^"]*)".*(?=Title)Title=(.*)(?='\]).*\r\n.*\r\n.*\r\n.*\r\n.*\r\n.*商城：(亚马逊中国)</div>
//	group1:url
//	group3:title
//	group5:商城
//	private String regexp = "<a href=\"([^\"]*)\".*(?=Title)Title=(.*)(?='\\]).*\\r\\n.*\\r\\n.*\\r\\n.*\\r\\n.*\\r\\n.*商城：(亚马逊中国)</div>";
	private String regexp = "<a href=\"([^\"]*)\".*(?=Title)Title=(.*)(?='\\]).*\\n.*\\n.*\\n.*\\n.*\\n.*商城：(亚马逊中国)</div>\n.*\n.*(\n.*\n.*)";
	private String url = "http://www.smzdm.com/page/1";
	private HttpClient httpClient;
	private GetMethod getMethod;
	
	@Autowired
	private InitHttpClient initHttpClient;
	@PersistenceContext
	private EntityManager em;
	@PostConstruct
	public void init(){
		httpClient = new HttpClient();
		try {
//			httpClient.startSession(new URL(url));
	        HostConfiguration conf = new HostConfiguration();
	        conf.setHost(new URI(url));
	        httpClient.setHostConfiguration(conf);
		}catch (URIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getMethod = new GetMethod();
	}
	
	public String trans(String sUrl){
		try {
			httpClient.getHostConfiguration().setHost(new URI(sUrl));
		} catch (URIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getMethod.recycle();
		getMethod.setPath(sUrl.substring(sUrl.indexOf("smzdm.com") + "smzdm.com".length()));
		try {
			httpClient.executeMethod(getMethod);
			String response = getMethod.getResponseBodyAsString();
			int i1 = response.indexOf("http://www.amazon");
			if(i1<0)
				return "";
			String tUrl = response.substring(i1, response.indexOf("'", i1));
			tUrl = tUrl.replaceAll("joyo01y-23", "weibo03-23");
			tUrl = tUrl.replaceAll("creative=3132", "creative=3200");
			return tUrl;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sUrl;
	}
	
	@Scheduled(cron="1/10 * * * * *")
	@Transactional
	public void excute(){
		getMethod.recycle();
		getMethod.setPath("/page/1");
		try {
			httpClient.executeMethod(getMethod);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = null;
		try {
			response = getMethod.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(response);
		while(matcher.find()){
			String url = matcher.group(1);
			String identi = url.substring(url.lastIndexOf("/") + 1);
			if(null != em.find(SmzdmIdentifi.class, identi))
				continue;
			else{
				SmzdmIdentifi smz = new SmzdmIdentifi();
				smz.setId(identi);
				em.persist(smz);
			}
			url = trans(url);
			System.out.println(url);
			String title = matcher.group(2);
			String storeType = matcher.group(3);
			String content = matcher.group(4);
			content = content.replaceAll("<a[^>]*href=\"([^\"]*)\"[^>]*>", "[[$1]]");
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
			content = content.replaceAll("\\[\\[\\]\\]", "");
			System.out.println(content);
			WeiboMsg wmsg = new WeiboMsg();
			wmsg.setContent(content);
			wmsg.setCreateDate(new Date());
			wmsg.setLastModifyDate(new Date());
			wmsg.setSource("smzdm");
			wmsg.setTitle(title);
			wmsg.setUrl(url);
			wmsg.setStoreType("storeType");
			em.merge(wmsg);
		}
	}
	
	public static void main(String[] args) throws Exception {
		Crawl3rdParty crawl = new Crawl3rdParty();
//		crawl.initHttpClient = new InitHttpClient();
//		crawl.initHttpClient.afterPropertiesSet();
		crawl.init();
		crawl.excute();
	}

}
