package com.redrum.webapp.weibo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import weibo4j.ShortUrl;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;

@Service
public class Crawl3rdParty {
	
	
//	<a href="([^"]+)".+Title=([^\n]+)'\][^\n]+\n[^\n]+\n[^\n]+商城：[^\n]*\n.*(美国亚马逊)[^\n]+\n[^\n]+\n[^\n]+\n([\s\S]+?)<div
//	<a href="([^"]+)".+Title=([^\n]+)'\][^\n]+\n[^\n]+\n[^\n]+\n[^\n]+\n[^\n]+\n[^\n]+\n[^\n]+商城：[^\n]*\n.*(亚马逊中国)[^\n]+\n[^\n]+\n[^\n]+\n([\s\S]+?)<div
//	group1:url
//	group2:title
//	group3:商城
//	private String regexp = "<a href=\"([^\"]*)\".*(?=Title)Title=(.*)(?='\\]).*\\r\\n.*\\r\\n.*\\r\\n.*\\r\\n.*\\r\\n.*商城：(亚马逊中国)</div>";
	private String[] regexp = new String[]{"<a href=\"([^\"]+)\".+Title=([^\\n]+)'\\][^\\n]+\\n[^\\n]+\\n[^\\n]+商城：[^\\n]*\\n.*(美国亚马逊)[^\\n]+\\n[^\\n]+\\n[^\\n]+\\n([\\s\\S]+?)<div"
			,"<a href=\"([^\"]+)\".+Title=([^\\n]+)'\\][^\\n]+\\n[^\\n]+\\n[^\\n]+\\n[^\\n]+\\n[^\\n]+\\n[^\\n]+\\n[^\\n]+商城：[^\\n]*\\n.*(亚马逊中国)[^\\n]+\\n[^\\n]+\\n[^\\n]+\\n([\\s\\S]+?)<div"
	};
	private String[] url = new String[]{"http://www.smzdm.com/page/1"
			,"http://www.smzdm.com/page/1"
	};
	private String[] storeType = new String[]{"亚马逊中国","美国亚马逊"};
//	private HttpClient httpClient;
//	private GetMethod getMethod;
	
	@Value("${access_token}")
	private String access_token ="2.00Lj6eJEiAwHIBdb114dc228iRIPyD";
	
	@Autowired
	private InitHttpClient initHttpClient;
	@PersistenceContext
	private EntityManager em;
	private Logger logger = Logger.getLogger(Crawl3rdParty.class);

	ScriptEngineManager factory = new ScriptEngineManager();
    ScriptEngine engine = factory.getEngineByName("JavaScript");
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
//		httpClient = new HttpClient();
//		try {
////			httpClient.startSession(new URL(url));
//	        HostConfiguration conf = new HostConfiguration();
//	        conf.setHost(new URI(url));
//	        httpClient.setHostConfiguration(conf);
//		}catch (URIException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		getMethod = new GetMethod();
	}
	
	public String trans(String sUrl){
//		try {
//			httpClient.getHostConfiguration().setHost(new URI(sUrl));
//		} catch (URIException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		HttpURLConnection c = getGetMethod(sUrl);
		c.setReadTimeout(60 * 1000);
//		getMethod.recycle();
//		getMethod.setPath(sUrl.substring(sUrl.indexOf("smzdm.com") + "smzdm.com".length()));
		try {
//			httpClient.executeMethod(getMethod);
//			String response = getMethod.getResponseBodyAsString();
			c.connect();
			String response = IOUtils.toString(c.getInputStream());
			c.disconnect();
//			int i1 = response.indexOf("http://www.amazon");
//			if(i1<0)
//				return "";
//			String tUrl = response.substring(i1, response.indexOf("'", i1));
			response = response.substring(response.indexOf("<script>") + "<script>".length(), response.indexOf("</script>"));
			response = response.replace("eval(function", "function red_f");
			response = response.replace("return p}", "return p};red_f");
			response = response.substring(0, response.length() - 2);
		    try {
				response = engine.eval(response) + "";
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    String tUrl = response.substring(response.indexOf("http"), response.indexOf("';ga('send','event"));
			tUrl = tUrl.replaceAll("joyo01y-23", "weibo0f0-23");
			tUrl = tUrl.replaceAll("joyo01y-20", "we042-20");
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
	
//	@Scheduled(cron="10 * * * * *")
	@Scheduled(fixedDelay=60000)
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
		logger.info("190");
		HttpURLConnection c = getGetMethod(url[i]);
		try {
			c.setReadTimeout(60 * 1000);
			c.connect();
			response = IOUtils.toString(c.getInputStream(), "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			c.disconnect();
			continue;
		}
		c.disconnect();
		response = response.replaceAll("什么值得买", "海淘");
		response = response.replaceAll("&nbsp;", " ");
		Pattern pattern = Pattern.compile(regexp[i]);
		Matcher matcher = pattern.matcher(response);
//		try {
//			FileUtils.write(new File("a.html"), response);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		if(0==i){
			logger.info("craw amazon start");
		}
		logger.info("216");
		while(matcher.find()){
			String url = matcher.group(1);
			String identi = url.substring(url.lastIndexOf("/") + 1);
			String storeType = matcher.group(3);
			if(url.indexOf("go")==-1)
				continue;
			url = trans(url);
			logger.debug(url);
			String title = matcher.group(2);
			String content = matcher.group(4);
			logger.info("227");
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
		
			logger.info("craw amazon end");
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		/*Crawl3rdParty crawl = new Crawl3rdParty();
//		crawl.initHttpClient = new InitHttpClient();
//		crawl.initHttpClient.afterPropertiesSet();
		crawl.init();
		crawl.excute();*/
//		URL url = new URL("http://www.dwz.cn/create.php");
//		HttpURLConnection c = (HttpURLConnection) url.openConnection();
//		c.setRequestMethod("POST");
//		c.setDoOutput(true);
//		IOUtils.write("access_type=web&url=http://weibo.com/3807150373/profile?topnav=1&wvr=5&user=1", c.getOutputStream(), "UTF-8");
//		System.out.println(IOUtils.toString(c.getInputStream()));
		
//		String sUrl = "a中bc文de";
//		sUrl = sUrl.replaceAll("[\u4e00-\u9fa5]+", ""); 
//		System.out.println(sUrl);
		
		
//		String s = FileUtils.readFileToString(new File("a.html"));
//		Pattern pattern = Pattern.compile("<a href=\"([^\"]+)\".+Title=([^'^\\n]+).+\\n.+\\n.+\\n.+\\n.+\\n.+商城：(亚马逊中国)</div>\\n.+\\n.+\\n([\\s\\S]+?)<div");
//		Matcher matcher = pattern.matcher(s);
//		System.out.println(matcher.find());
		

//		final WebClient webClient = new WebClient();
//		HtmlPage page = webClient.getPage("http://blank");
		String script = "var red_f = function(p,a,c,k,e,d){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--){d[e(c)]=k[c]||e(c)}k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1};while(c--){if(k[c]){p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c])}}return p};";
		script = "function red_f(p,a,c,k,e,d){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--){d[e(c)]=k[c]||e(c)}k=[function(e){return d[e]}];e=function(){return'\\\\w+'};c=1};while(c--){if(k[c]){p=p.replace(new RegExp('\\\\b'+e(c)+'\\\\b','g'),k[c])}}return p}red_f('(3(){(3(i,s,o,g,r,a,m){i[\\'C\\']=r;i[r]=i[r]||3(){(i[r].q=i[r].q||[]).B(A)},i[r].l=1*z D();a=s.E(o),m=s.I(o)[0];a.H=1;a.y=g;m.F.J(a,m)})(k,x,\\'u\\',\\'//d.v-9.5/9.w\\',\\'2\\');2(\\'p\\',\\'n-G-4\\',\\'K.5\\');W 7=V;3 6(){Z(7)12;7=11;b.j=c}2(\\'10\\',\\'Y\\',b.j);2(\\'h\\',\\'M\\');c=\\'L://d.f.5/P/R/T/?t=8-e&N=8-e\\';2(\\'h\\',\\'Q\\',\\'直达链接\\',\\'S\\',\\'f.5\\',{\\'O\\':6});U(6,X)})()',62,65,'||ga|function||com|redirect|redirected|joyo01y|analytics||location|byroga|www|20|amazon||send||href|window|||UA||create|||||script|google|js|document|src|new|arguments|push|GoogleAnalyticsObject|Date|createElement|parentNode|27058866|async|getElementsByTagName|insertBefore|smzdm|http|pageview|tag|hitCallback|gp|event|product|ht|B00FQHF7IW|setTimeout|false|var|0|page|if|set|true|return'.split('|'),0,{})";
//		HtmlUnitDriver driver = new HtmlUnitDriver(true);
//		driver.get("http://www.baidu.com");
//		script += "red_f('(3(){(3(i,s,o,g,r,a,m){i[\\'E\\']=r;i[r]=i[r]||3(){(i[r].q=i[r].q||[]).D(C)},i[r].l=1*B F();a=s.G(o),m=s.J(o)[0];a.I=1;a.H=g;m.A.p(a,m)})(n,u,\\'z\\',\\'//7.v-d.e/d.x\\',\\'2\\');2(\\'L\\',\\'w-K-4\\',\\'10.e\\');Y 5=W;3 6(){12(5)11;5=14;c.9=8}2(\\'13\\',\\'U\\',c.9);2(\\'f\\',\\'V\\');8=\\'M://7.P.k.j/Q/T/S/?t=b-h&R=b-h\\';2(\\'f\\',\\'N\\',\\'直达链接\\',\\'O\\',\\'k.j\\',{\\'X\\':6});y(6,Z)})()',62,67,'||ga|function||redirected|redirect|www|autyfl|href||joyo010b|location|analytics|com|send||22||jp|co|||window||insertBefore|||||document|google|UA|js|setTimeout|script|parentNode|new|arguments|push|GoogleAnalyticsObject|Date|createElement|src|async|getElementsByTagName|27058866|create|http|event|ht|amazon|gp|tag|B0090E6OXY|product|page|pageview|false|hitCallback|var|1000|smzdm|return|if|set|true'.split('|'),0,{})";
//		Object scriptResult = driver.executeScript(script);
//		
//		System.out.println(driver.getCurrentUrl());
		
		
		ScriptEngineManager factory = new ScriptEngineManager();
	    ScriptEngine engine = factory.getEngineByName("JavaScript");
	    engine.eval(script);
	}
	

}
