package com.redrum.webapp.htmlunit;

import java.io.IOException;
import java.net.MalformedURLException;






import java.util.concurrent.TimeUnit;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class Test {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.getProperties().put("org.apache.commons.logging.simplelog.defaultlog", "debug");
	    final WebClient webClient = new WebClient();
	    webClient.setHTMLParserListener(HTMLParserListener.LOG_REPORTER);
	    webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36");
//	    webClient.addRequestHeader("Referer", "http://www.haitaozj.com/");
//	    webClient.getCookieManager().addCookie(new Cookie("www.haitaozj.com", "", "OD4B_2132_saltkey=oi0Tje5g; OD4B_2132_lastrequest=63930wPvRajwI9tvQlJPrDmygGUjm8gQP56zsTxqK38AVY7p2%2Be%2B"));
//	    webClient.addRequestHeader("Cookie", "OD4B_2132_saltkey=oi0Tje5g; OD4B_2132_lastrequest=63930wPvRajwI9tvQlJPrDmygGUjm8gQP56zsTxqK38AVY7p2%2Be%2B");
	    HtmlPage page = webClient.getPage("http://www.haitaozj.com/");
//	    Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
//	    webClient.waitForBackgroundJavaScript(10000);
	    System.out.println(page.getTitleText());
//	    page.executeJavaScript("document.location.reload()");
	    page = (HtmlPage) page.refresh();
//	    System.out.println(webClient.);
//	    System.out.println(webClient.getCookieManager().getCookies());
//	    Thread.sleep(5000);
	    System.out.println(page.getTitleText());
	    Thread.sleep(3000);
	    DomElement e = page.getElementByName("username");
	    e.setNodeValue("jobpassion");
	    e = page.getElementByName("password");
	    e.setNodeValue("8023huahua");
	    e = page.getElementById("lsform");
//	    page = ((HtmlForm)e).getElementsByTagName("button").get(0).click();
	    page.executeJavaScript("pwmd5('ls_password');");
	    page.executeJavaScript("lsSubmit();");
//	    page.setDocumentURI("http://www.haitaozj.com/home.php?mod=space&do=notice");
	    System.out.println(webClient.getCookieManager().getCookies());
	    page = webClient.getPage("http://www.haitaozj.com/home.php?mod=space&do=notice");
	    
	    Thread.sleep(5000);
	    System.out.println(page.getTitleText());
//	    System.out.println(e);
//	    HtmlSubmitInput button = e.getInputByName("submitbutton");
//	    final String pageAsXml = page.asXml();
//	    Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
//	    System.out.println();

//	    final String pageAsText = page.asText();
//	    Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));

	    webClient.closeAllWindows();
	}

}
