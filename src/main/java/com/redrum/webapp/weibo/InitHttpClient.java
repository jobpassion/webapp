package com.redrum.webapp.weibo;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class InitHttpClient {

	private HttpMethod httpMethod;
	private PostMethod postMethod;
	private HttpClient httpClient;
	private String ck;

	public String getCk() {
		return ck;
	}

	public void setCk(String ck) {
		this.ck = ck;
	}

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
	
	public void initCookie(){

		try {
			System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
			driver = new ChromeDriver();
			driver.get("http://weibo.com/");
			WebElement e = driver.findElement(By.name("username"));
			e.sendKeys("jobpassion@gmail.com");
			e = driver.findElement(By.name("password"));
			e.sendKeys("8023huahua");
			e = driver.findElement(By
					.xpath("//span[@node-type='submitStates']"));
			e.click();
			Set<Cookie> cookies = driver.manage().getCookies();
			// httpClient.startSession(new URL("http://weibo.com"));
			HttpState state = new HttpState();
			ck = "";
			for (Cookie c : cookies) {
				ck += c.getName() + "=" + c.getValue() + "; ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	WebDriver driver = null;

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
//		ck = "__utma=15428400.1695645915.1351319378.1351319378.1351319378.1; SINAGLOBAL=1743311276659.3694.1359040897731; ssoln=18260402168%40139.com; myuid=2350795254; un=jobpassion@gmail.com; wvr=5; UOR=www.juyouqu.com,widget.weibo.com,login.sina.com.cn; UV5PAGE=usr513_160; SUE=es%3D6edfb5cebc465646c2b99373cb2a29fe%26ev%3Dv1%26es2%3D2d0e68c7608a20158aa2045b5dfb59ee%26rs0%3DcEHTC0Ck28iBwSm7ReH2rIUG1n%252FJjjyJ3TkFC8oU2gEFF7qEa83xCOp%252FdFSR05fBeMEZuKAm5ZMBr721u67n7Rfq7KrNI7cHkJFqnvrl1ut%252Bid0pcoUAmyM9JP316GEN%252FB9E1AqHJns7jVNcflxekc2QPaMOf4BzOUX7zShB0LM%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1383912034%26et%3D1383998434%26d%3Dc909%26i%3D15d3%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D2%26st%3D0%26uid%3D3807150373%26name%3Djobpassion%2540gmail.com%26nick%3D%25E6%25B5%25B7%25E6%25B7%2598%25E8%25B5%2584%25E8%25AE%25AF2013%26fmp%3D%26lcp%3D2013-10-08%252021%253A20%253A24; SUS=SID-3807150373-1383912034-JA-jqf3k-628deb26b5773de29473c3d67d64b10b; ALF=1386504033; SSOLoginState=1383912034; UUG=usrmdins41458; _s_tentry=weibo.com; Apache=8601501327939.331.1383912035126; ULV=1383912035149:97:5:3:8601501327939.331.1383912035126:1383837545412; UV5=usrmdins311164; SinaRot_wb_r_topic=75";
		ck = null;
		HostConfiguration conf = new HostConfiguration();
		conf.setHost(new URI("http://weibo.com"));
		httpClient = new HttpClient();
		httpClient.setHostConfiguration(conf);
		try {
			httpMethod = new GetMethod();
			httpMethod.setPath("/");
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
			httpMethod.addRequestHeader("Cookie", ck);
//			httpClient.executeMethod(httpMethod);
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
				postMethod.addRequestHeader("Cookie", ck);
			
			if (!checkWeibo() && count<3){
				count++;
				driver.quit();
				afterPropertiesSet();
			}else{
				driver.quit();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public boolean checkWeibo(){
		if(null == ck)
			return false;
		try {
		    resetMethod();
			httpMethod.setPath("/");
			httpClient.executeMethod(httpMethod);
			String response = new String(httpMethod.getResponseBody(), "UTF-8");
			boolean succ = response.indexOf("我的首页") > 0;
			System.out.println(succ);
			return succ;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}

	@Scheduled(cron = "1 */5 * * * *")
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
		httpMethod.recycle();
		for(Header h:httpMethod.getRequestHeaders()){
			httpMethod.removeRequestHeader(h);
		}
		httpClient.getState().clearCookies();
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
//		ck = "__utma=15428400.1695645915.1351319378.1351319378.1351319378.1; SINAGLOBAL=1743311276659.3694.1359040897731; ssoln=18260402168%40139.com; myuid=2350795254; un=jobpassion@gmail.com; wvr=5; UOR=www.juyouqu.com,widget.weibo.com,login.sina.com.cn; UV5PAGE=usr513_160; SUE=es%3D6edfb5cebc465646c2b99373cb2a29fe%26ev%3Dv1%26es2%3D2d0e68c7608a20158aa2045b5dfb59ee%26rs0%3DcEHTC0Ck28iBwSm7ReH2rIUG1n%252FJjjyJ3TkFC8oU2gEFF7qEa83xCOp%252FdFSR05fBeMEZuKAm5ZMBr721u67n7Rfq7KrNI7cHkJFqnvrl1ut%252Bid0pcoUAmyM9JP316GEN%252FB9E1AqHJns7jVNcflxekc2QPaMOf4BzOUX7zShB0LM%253D%26rv%3D0; SUP=cv%3D1%26bt%3D1383912034%26et%3D1383998434%26d%3Dc909%26i%3D15d3%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D2%26st%3D0%26uid%3D3807150373%26name%3Djobpassion%2540gmail.com%26nick%3D%25E6%25B5%25B7%25E6%25B7%2598%25E8%25B5%2584%25E8%25AE%25AF2013%26fmp%3D%26lcp%3D2013-10-08%252021%253A20%253A24; SUS=SID-3807150373-1383912034-JA-jqf3k-628deb26b5773de29473c3d67d64b10b; ALF=1386504033; SSOLoginState=1383912034; UUG=usrmdins41458; _s_tentry=weibo.com; Apache=8601501327939.331.1383912035126; ULV=1383912035149:97:5:3:8601501327939.331.1383912035126:1383837545412; UV5=usrmdins311164; SinaRot_wb_r_topic=75"
		httpMethod.addRequestHeader("Cookie", ck);

		postMethod.recycle();
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
		postMethod.addRequestHeader("Cookie", ck);
	}

}
