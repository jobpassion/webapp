package com.redrum.webapp.weibo;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import javax.annotation.PostConstruct;

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

	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		try {
			System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.get("http://weibo.com/");
			WebElement e = driver.findElement(By.name("username"));
			e.sendKeys("jobpassion@gmail.com");
			e = driver.findElement(By.name("password"));
			e.sendKeys("8023huahua");
			e = driver.findElement(By
					.xpath("//span[@node-type='submitStates']"));
			e.click();
			Set<Cookie> cookies = driver.manage().getCookies();
			httpClient = new HttpClient();
			HostConfiguration conf = new HostConfiguration();
			conf.setHost(new URI("http://weibo.com"));
			httpClient.setHostConfiguration(conf);
			// httpClient.startSession(new URL("http://weibo.com"));
			HttpState state = new HttpState();
			ck = "";
			for (Cookie c : cookies) {
				ck += c.getName() + "=" + c.getValue() + "; ";
			}
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			httpClient.executeMethod(httpMethod);
			String response = httpMethod.getResponseBodyAsString();
			boolean succ = response.indexOf("我的首页") > 0;
			System.out.println(succ);
			if (!succ && count<3){
				count++;
				afterPropertiesSet();
			}
			else {
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
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Scheduled(cron = "1 */5 * * * *")
	private void refreshCookie() {
		resetMethod();
		try {
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
