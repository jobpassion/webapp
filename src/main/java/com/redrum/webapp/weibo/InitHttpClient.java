package com.redrum.webapp.weibo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import net.sourceforge.htmlunit.corejs.javascript.NativeObject;

import org.apache.bcel.generic.ACONST_NULL;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.redrum.webapp.weibo.entity.WeiboAccount;

@Service
public class InitHttpClient {

	private HttpMethod httpMethod;
	private PostMethod postMethod;
	private HttpClient httpClient;
	// private String ck;
	@Autowired
	private WeiboService weiboService;
	public WeiboAccount account;
	private Map<String,HttpClient> clientMap = new HashMap();
	private Logger logger = Logger.getLogger(InitHttpClient.class);

	// public String getCk() {
	// return ck;
	// }
	//
	// public void setCk(String ck) {
	// this.ck = ck;
	// }

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public PostMethod getPostMethod() {
		return postMethod;
	}

	int count = 0;

	public void initCookie() {

		// try {
		// System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
		// driver = new ChromeDriver();
		// driver.get("http://weibo.com/");
		// WebElement e = driver.findElement(By.name("username"));
		// e.sendKeys(account.getUsername());
		// e = driver.findElement(By.name("password"));
		// e.sendKeys(account.getPassword());
		// e = driver.findElement(By
		// .xpath("//span[@node-type='submitStates']"));
		// e.click();
		// Set<Cookie> cookies = driver.manage().getCookies();
		// // httpClient.startSession(new URL("http://weibo.com"));
		// HttpState state = new HttpState();
		// String ck = "";
		// for (Cookie c : cookies) {
		// ck += c.getName() + "=" + c.getValue() + "; ";
		// }
		// account.setCookie(ck);
		// driver.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		try {
			HttpClient httpClient = new HttpClient();
			httpClient.setTimeout(30000);
			HostConfiguration conf = new HostConfiguration();
			conf.setHost(new URI("http://www.weibo.com"));
			httpClient.setHostConfiguration(conf);
			httpClient.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			httpClient.getParams().setBooleanParameter(
					HttpMethodParams.SINGLE_COOKIE_HEADER, true);
			httpClient.getParams().setCookiePolicy(
					CookiePolicy.BROWSER_COMPATIBILITY);
			GetMethod method = new GetMethod(
					"http://login.sina.com.cn/sso/prelogin.php?entry=weibo&callback=sinaSSOController.preloginCallBack&su=&rsakt=mod&client=ssologin.js(v1.4.11)&_=1387711657507");
			httpClient.executeMethod(method);
			String s = method.getResponseBodyAsString();
			int idx;
			idx = s.indexOf("servertime\":") + "servertime\":".length();
			String servertime = s.substring(idx, s.indexOf(",", idx));
			idx = s.indexOf("nonce\":\"") + "nonce\":\"".length();
			String nonce = s.substring(idx, s.indexOf("\"", idx));
			idx = s.indexOf("pubkey\":\"") + "pubkey\":\"".length();
			String pubkey = s.substring(idx, s.indexOf("\"", idx));
			idx = s.indexOf("rsakv\":\"") + "rsakv\":\"".length();
			String rsakv = s.substring(idx, s.indexOf("\"", idx));
			PostMethod post = new PostMethod(
					"http://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.11)");

			final WebClient webClient = new WebClient();
			HtmlPage page = webClient.getPage("http://www.baidu.com/");
			InputStream is = Testweibo.class.getClassLoader()
					.getResourceAsStream("weibo.js");
			String script = IOUtils.toString(is);
			is.close();
			ScriptResult sr = page.executeJavaScript(script);
			script = "sinaSSOController.rsaPubkey = '"
					+ pubkey
					+ "';sinaSSOController.servertime = '"
					+ servertime
					+ "';sinaSSOController.nonce = '"
					+ nonce
					+ "';sinaSSOController.rsakv = '"
					+ rsakv
					+ "';sinaSSOController.from = 'weibo';sinaSSOController.useTicket = 1;";
			sr = page.executeJavaScript(script);
			script = "sinaSSOController.login('" + account.getUsername()
					+ "','" + account.getPassword() + "',7);";
			sr = page.executeJavaScript(script);
			NativeObject no = (NativeObject) sr.getJavaScriptResult();

			for (Object o : no.getAllIds()) {
				// System.out.println(o + "=>" + no.get(o));
				post.setParameter(o + "", no.get(o) + "");
			}
			webClient.closeAllWindows();
			post.setParameter("gateway", "1");
			post.setParameter("savestate", "7");
			post.setParameter("useticket", "1");
			post.setParameter(
					"pagerefer",
					"http://login.sina.com.cn/sso/logout.php?entry=miniblog&r=http%3A%2F%2Fweibo.com%2Flogout.php%3Fbackurl%3D%252F");
			post.setParameter("vsnf", "1");
			post.setParameter("encoding", "UTF-8");
			post.setParameter("prelt", "140");
			post.setParameter(
					"url",
					"http://www.weibo.com/ajaxlogin.php?framelogin=1&callback=parent.sinaSSOController.feedBackUrlCallBack");
			post.setParameter("returntype", "META");
			httpClient.executeMethod(post);
			s = new String(post.getResponseBody(), "UTF-8");
			idx = s.indexOf("location.replace(\"")
					+ "location.replace(\"".length();
			String url = s.substring(idx, s.indexOf("\"", idx));
//			System.out.println(url);
			logger.debug(url);
			method = new GetMethod(url);
			httpClient.executeMethod(method);
			;
//			System.out.println(method.getResponseBodyAsString());
			logger.debug(method.getResponseBodyAsString());
//			 method = new GetMethod("http://weibo.com/");
//			 httpClient.executeMethod(method);
//			 System.out.println(method.getURI());
//			 System.out.println(new String(method.getResponseBody(),"UTF-8"));

//			String ck = "";
//			for (Cookie c : httpClient.getState().getCookies()) {
////				if(c.getDomain().equals(".weibo.com")||c.getDomain().equals("weibo.com"))
//				ck += c.getName() + "=" + c.getValue() + "; ";
//			}
//			account.setCookie(ck);
			clientMap.put(account.getUsername(), httpClient);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	WebDriver driver = null;

	// @PostConstruct
	public void init() {
		initCookie();
	}

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		Protocol.registerProtocol("http", new Protocol("http",
				new OSProtocolSocketFactory(), 80));

		httpClient = new HttpClient();
		httpClient.setTimeout(30000);
		HostConfiguration conf = new HostConfiguration();
		conf.setHost(new URI("http://weibo.com"));
		httpClient.setHostConfiguration(conf);

		httpClient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		httpClient.getParams().setBooleanParameter(
				HttpMethodParams.SINGLE_COOKIE_HEADER, true);
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
//		 if(null == System.getProperty("env"))
//			 if(true)
//		 return;
//		System.out.println(System.getProperty("env"));
		logger.info(System.getProperty("env"));
		intervalCheck();
		// ck =
		// "__utma=15428400.1695645915.1351319378.1351319378.1351319378.1; SINAGLOBAL=1743311276659.3694.1359040897731; ssoln=18260402168%40139.com; myuid=2350795254; un=jobpassion@gmail.com; wvr=5; SinaRot_wb_r_topic=84; UV5PAGE=usr511_196; SUE=es%3D6f93acac95733bd5c77d2752300965da%26ev%3Dv1%26es2%3D1be20b8b8e69fb068e7a0ba6fc8ceae4%26rs0%3Dm9CUmlvoVqKlPMc7pNcK6xtfel75%252FhJveinSyZsCdQb1ruTbsb9Csi6Qn3BdmBVdshCUdX7K%252BMwYsznrL51YNDAYNBMcuMbDFtT7g4DO7rFh9C3OolCF%252FpY5eaUwHJj8X80HLgfZzpYq09wgOtACYCKIZu%252FlcRdvO0cFdL3TAQQ%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1384174580%26et%3D1384260980%26d%3Dc909%26i%3Da6d4%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D2%26st%3D0%26uid%3D3807150373%26name%3Djobpassion%2540gmail.com%26nick%3D%25E6%25B5%25B7%25E6%25B7%2598%25E8%25B5%2584%25E8%25AE%25AF2013%26fmp%3D%26lcp%3D2013-10-08%252021%253A20%253A24; SUS=SID-3807150373-1384174580-XD-vc5by-adf6a9ecb6d31faa79c1aeb9e72cc849; ALF=1386766580; SSOLoginState=1384174580; UUG=usrmdins41456; _s_tentry=login.sina.com.cn; UOR=www.juyouqu.com,widget.weibo.com,login.sina.com.cn; Apache=1184292640537.0234.1384174581147; ULV=1384174581208:100:8:2:1184292640537.0234.1384174581147:1384080610846; UV5=usrmdins312_148";
		// ck = null;
		// URL url = new URL("http://www.baidu.com");
		// URLConnection connection = url.openConnection();
		// connection.connect();
		// System.out.println(IOUtils.readLines(connection.getInputStream()));
	}

	public boolean checkWeibo() {
//		if (null == account.getCookie()) {
		if (null == clientMap.get(account.getUsername())) {
//			System.out.println("false");
			logger.info("false");
			return false;
		}
		try {
			resetMethod();
			httpMethod.setPath("/");
			httpClient.executeMethod(httpMethod);
			String response = "";
			if(httpMethod.getStatusCode()==200)
			{
				
//				logger.info("check httpclient 200");
				try{
				response= new String(httpMethod.getResponseBody(), "UTF-8");
				}catch(Exception e){
					logger.error(e.getMessage());
				}
			}else{
				logger.info("check httpClient " + httpMethod.getStatusCode());
			}
			// HttpURLConnection connection =
			// resetGetConnection("http://weibo.com/");
			// connection.connect();
			// String response = IOUtils.toString(connection.getInputStream(),
			// "UTF-8");
			// connection.disconnect();
//			System.out.println(response);
			boolean succ = response.indexOf("我的首页") > 0;
//			System.out.println(succ);
			logger.info(succ);
			httpMethod.releaseConnection();
			// System.out.println(response);
			return succ;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}

//	 @Scheduled(cron = "1 0/5 * * * *")
	private void refreshCookie() {
		resetMethod();
		try {
			httpMethod.setPath("/");
			httpClient.executeMethod(httpMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void resetMethod() {
//		httpClient.getState().clearCookies();
		httpClient = clientMap.get(account.getUsername());
//		for(String s1:account.getCookie().split("; ")){
//			Cookie cookie = new Cookie(domain, name, value)
//			httpClient.getState().addCookie(cookie);
//		}
		httpMethod = new GetMethod();
//		for (Header h : httpMethod.getRequestHeaders()) {
//			httpMethod.removeRequestHeader(h);
//		}
//		httpClient.getState().clearCookies();
		httpMethod
				.addRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
		httpMethod.addRequestHeader("Host", "weibo.com");
		httpMethod.addRequestHeader("Connection", "keep-alive");
		httpMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpMethod.addRequestHeader("Cache-Control", "max-age=0");
		httpMethod
				.addRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpMethod.addRequestHeader("Accept-Encoding", "deflate,sdch");
		httpMethod.removeRequestHeader("Cookie");
		// ck =
		// "__utma=15428400.1695645915.1351319378.1351319378.1351319378.1; SINAGLOBAL=1743311276659.3694.1359040897731; ssoln=18260402168%40139.com; myuid=2350795254; un=jobpassion@gmail.com; wvr=5; UOR=www.juyouqu.com,widget.weibo.com,login.sina.com.cn; UV5PAGE=usr513_160; SUE=es%3D6edfb5cebc465646c2b99373cb2a29fe%26ev%3Dv1%26es2%3D2d0e68c7608a20158aa2045b5dfb59ee%26rs0%3DcEHTC0Ck28iBwSm7ReH2rIUG1n%252FJjjyJ3TkFC8oU2gEFF7qEa83xCOp%252FdFSR05fBeMEZuKAm5ZMBr721u67n7Rfq7KrNI7cHkJFqnvrl1ut%252Bid0pcoUAmyM9JP316GEN%252FB9E1AqHJns7jVNcflxekc2QPaMOf4BzOUX7zShB0LM%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1383912034%26et%3D1383998434%26d%3Dc909%26i%3D15d3%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D2%26st%3D0%26uid%3D3807150373%26name%3Djobpassion%2540gmail.com%26nick%3D%25E6%25B5%25B7%25E6%25B7%2598%25E8%25B5%2584%25E8%25AE%25AF2013%26fmp%3D%26lcp%3D2013-10-08%252021%253A20%253A24; SUS=SID-3807150373-1383912034-JA-jqf3k-628deb26b5773de29473c3d67d64b10b; ALF=1386504033; SSOLoginState=1383912034; UUG=usrmdins41458; _s_tentry=weibo.com; Apache=8601501327939.331.1383912035126; ULV=1383912035149:97:5:3:8601501327939.331.1383912035126:1383837545412; UV5=usrmdins311164; SinaRot_wb_r_topic=75"
//		httpMethod.addRequestHeader("Cookie", account.getCookie());

//		postMethod.recycle();
		postMethod = new PostMethod();
		postMethod
				.addRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
		postMethod.addRequestHeader("Host", "weibo.com");
		postMethod.addRequestHeader("Connection", "keep-alive");
		postMethod.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		// postMethod.addRequestHeader("Cache-Control", "max-age=0");
		postMethod.addRequestHeader("Accept", "*/*");
		postMethod.addRequestHeader("Accept-Encoding", "deflate,sdch");
		postMethod.addRequestHeader("Origin", "http://weibo.com");
		// postMethod.addRequestHeader("Content-Length", "101");
		postMethod.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		postMethod.addRequestHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.addRequestHeader("Referer",
				"http://weibo.com/xiena?refer=interest");
//		postMethod.addRequestHeader("Cookie", account.getCookie());

	}
	

	@Scheduled(cron = "0 0/5 * * * *")
	public void intervalCheck(){

		List<WeiboAccount> accounts = weiboService.queryAccounts();
		for (int i = 0; i < accounts.size(); i++) {
			account = accounts.get(i);
			if (checkWeibo()) {
				continue;
			}
			initCookie();

			try {
				httpMethod = new GetMethod();
				httpMethod.setPath("/");
				httpMethod
						.addRequestHeader(
								"User-Agent",
								"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
				httpMethod.addRequestHeader("Host", "weibo.com");
				httpMethod.addRequestHeader("Connection", "keep-alive");
				httpMethod
						.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
				httpMethod.addRequestHeader("Cache-Control", "max-age=0");
				httpMethod
						.addRequestHeader("Accept",
								"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				httpMethod.addRequestHeader("Accept-Encoding", "deflate,sdch");
//				httpMethod.addRequestHeader("Cookie", account.getCookie());
				// httpClient.executeMethod(httpMethod);
				postMethod = new PostMethod();

				postMethod
						.addRequestHeader(
								"User-Agent",
								"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
				postMethod.addRequestHeader("Host", "weibo.com");
				postMethod.addRequestHeader("Connection", "keep-alive");
				postMethod
						.addRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
				postMethod.addRequestHeader("Cache-Control", "max-age=0");
				postMethod
						.addRequestHeader("Accept",
								"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				postMethod.addRequestHeader("Accept-Encoding", "deflate,sdch");
//				postMethod.addRequestHeader("Cookie", account.getCookie());

				if (!checkWeibo() && count < 1) {
					count++;
					// driver.quit();
					// afterPropertiesSet();
					i--;
				} else {
					// driver.quit();
					weiboService.save(account);
					count = 0;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	public HttpURLConnection resetGetConnection(String url) {

		try {
			getUrl = new URL(url);
			HttpURLConnection getConnection = (HttpURLConnection) getUrl
					.openConnection();
			getConnection
					.setRequestProperty(
							"User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
			getConnection.setRequestProperty("Host", "weibo.com");
			getConnection.setRequestProperty("Connection", "keep-alive");
			getConnection.setRequestProperty("Accept-Language",
					"zh-CN,zh;q=0.8");
			getConnection.setRequestProperty("Cache-Control", "max-age=0");
			getConnection
					.setRequestProperty("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			getConnection.setRequestProperty("Accept-Encoding", "deflate,sdch");
			getConnection.setRequestProperty("Cookie", account.getCookie());
			return getConnection;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public HttpURLConnection resetPostConnection(String url) {

		try {
			posturl = new URL(url);
			HttpURLConnection getConnection = (HttpURLConnection) posturl
					.openConnection();
			getConnection.setRequestMethod("POST");
			getConnection
					.setRequestProperty(
							"User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
			getConnection.setRequestProperty("Host", "www.weibo.com");
			getConnection.setRequestProperty("Connection", "keep-alive");
			// getConnection.setRequestProperty("Accept-Language",
			// "zh-CN,zh;q=0.8");
			// getConnection.setRequestProperty("Cache-Control", "max-age=0");
			getConnection.setRequestProperty("Accept", "*/*");
			// getConnection.setRequestProperty("Accept-Encoding",
			// "deflate,sdch");
			getConnection.setRequestProperty("Cookie", account.getCookie());
			getConnection.setRequestProperty("Origin", "http://www.weibo.com");
			getConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			getConnection.setRequestProperty("X-Requested-With",
					"XMLHttpRequest");
			getConnection.setRequestProperty("Referer",
					"http://www.weibo.com/minipublish?uid=3807150373");
			getConnection.setDoOutput(true);
			return getConnection;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public URL getUrl;
	public URL posturl;

}
