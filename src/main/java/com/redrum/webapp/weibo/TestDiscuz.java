package com.redrum.webapp.weibo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.stereotype.Service;

public class TestDiscuz {
	private HttpClient httpClient;
	private GetMethod getMethod;
	private PostMethod postMethod;
	public String username = "jobpassion";
	public String password = "67a75727740fc50e8b7f3e4c452244e5";
	public String fid = "95";
	public String typeid = "125";

	private void init() {
		httpClient = new HttpClient();
		getMethod = new GetMethod();
		postMethod = new PostMethod();
		HttpState state = new HttpState();
		httpClient.setState(state);
		List headers = new ArrayList();
		headers.add(new Header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.49 Safari/537.36"));
		httpClient.getHostConfiguration().getParams()
				.setParameter("http.default-headers", headers);
		httpClient.setTimeout(10000);
	}

	private void post(String baseUrl) throws Exception {
		getMethod.setURI(new URI(baseUrl));
		httpClient.executeMethod(getMethod);
		System.out.println(getMethod.getResponseBodyAsString());
		getMethod.releaseConnection();
		httpClient.executeMethod(getMethod);
		postMethod.setURI(new URI(baseUrl + "/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1"));
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		postMethod.setParameter("username", username);
		postMethod.setParameter("password", password);
		postMethod.setParameter("quickforward", "yes");
		postMethod.setParameter("handlekey", "ls");
		httpClient.executeMethod(postMethod);
		System.out.println(postMethod.getResponseBodyAsString());
		postMethod.releaseConnection();
		httpClient.executeMethod(postMethod);
		System.out.println(postMethod.getResponseBodyAsString());
		postMethod.releaseConnection();
		getMethod.releaseConnection();
		getMethod.setURI(new URI(baseUrl + "/forum.php?mod=viewthread&tid=107757&extra=page%3D2%26orderby%3Dlastpost"));
		httpClient.executeMethod(getMethod);
		String response = getMethod.getResponseBodyAsString();
		System.out.println(response.substring(
				response.indexOf("formhash=") + 8,
				response.indexOf("formhash=") + 18));
		String formhash = response.substring(response.indexOf("formhash=") + 9,
				response.indexOf("formhash=") + 17);
		System.out.println(response.indexOf("forum.php"));
		getMethod.releaseConnection();
		
		postMethod = new PostMethod();
		postMethod.setURI(new URI(baseUrl + "/misc.php?mod=swfupload&action=swfupload&operation=upload&fid=" + fid));
		File tmpF = new File("1.jpg");
		BufferedImage image = ImageIO.read(new URL("http://www.baidu.com/img/bdlogo.gif"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( image, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		FileUtils.writeByteArrayToFile(tmpF, imageInByte);
        FilePart fp = new FilePart("Filedata", tmpF);  
        Part[] parts = {fp,new StringPart("Filename", "1.jpg"),new StringPart("type", "image"),new StringPart("uid", "1"),new StringPart("filetype", "jpg"),new StringPart("hash", "adb7e0d5960d3e3bf93810bab78620e7"),new StringPart("Upload", "Submit Query")};  
        MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());  
        postMethod.setRequestEntity(mre);
        httpClient.executeMethod(postMethod);
        String imgf = postMethod.getResponseBodyAsString();
		postMethod=  new PostMethod();
		postMethod.setURI(new URI(baseUrl + "/forum.php?mod=post&action=newthread&fid=" + fid + "&topicsubmit=yes&infloat=yes&handlekey=fastnewpost&inajax=1&formhash=" + formhash));
		postMethod.addParameter("message", "大家晚上都在玩什么呢");
		postMethod.addParameter("subject", "工作好累啊");
		postMethod.addParameter("attachnew[" + imgf + "][description]", "");
		RequestEntity r;
		UrlEncodedFormEntity uef;
		postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
		postMethod.setRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.49 Safari/537.36");
		postMethod.setRequestHeader("Origin", baseUrl);
		postMethod.setRequestHeader("Accept-Encoding", "deflate,sdch");
		postMethod
				.setRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		postMethod.setRequestHeader("Referer",baseUrl + "/forum.php?mod=viewthread&tid=107757&extra=page%3D2%26orderby%3Dlastpost");
		httpClient.executeMethod(postMethod);
		System.out.println(postMethod.getResponseBodyAsString());
	}

	public static void main(String[] args) throws Exception {
		TestDiscuz e = new TestDiscuz();
		e.init();
		e.username = "admin";
		e.password = "admin";
		e.fid = "2";
		e.post("http://localhost/php");
//		e.post("http://www.haitaozj.com");
	}
}
