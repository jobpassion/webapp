package com.redrum.webapp.weibo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
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
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecuteHtzj {
	private HttpClient httpClient;
	private GetMethod getMethod;
	private PostMethod postMethod;
	public String username = "jobpassion";
	public String password = "67a75727740fc50e8b7f3e4c452244e5";
	public String fid = "100";
	public String typeid = "105";
	private WeiboMsg wm;
	@Autowired
	private InitHttpClient initHttpClient;
	private Logger logger = Logger.getLogger(ExecuteHtzj.class);

//	@PostConstruct
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

//		httpClient.getParams().setParameter(
//				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		httpClient.getParams().setBooleanParameter(
				HttpMethodParams.SINGLE_COOKIE_HEADER, true);
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		httpClient.setTimeout(30000);
	}

	private void post(String baseUrl) {
		init();
		try {
			getMethod.setURI(new URI(baseUrl));
		} catch (URIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			httpClient.executeMethod(getMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(getMethod.getResponseBodyAsString());
		getMethod.releaseConnection();
		try {
			httpClient.executeMethod(getMethod);
			postMethod.setURI(new URI(baseUrl + "/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1"));
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		postMethod.setParameter("username", username);
		postMethod.setParameter("password", password);
		postMethod.setParameter("quickforward", "yes");
		postMethod.setParameter("handlekey", "ls");
		try {
			httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(postMethod.getResponseBodyAsString());
		postMethod.releaseConnection();
		try {
			httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(postMethod.getResponseBodyAsString());
		postMethod.releaseConnection();
		getMethod.releaseConnection();
		try {
			getMethod.setURI(new URI(baseUrl + "/forum.php?mod=viewthread&tid=107757&extra=page%3D2%26orderby%3Dlastpost"));
			httpClient.executeMethod(getMethod);
		} catch (URIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		logger.debug(response.substring(
				response.indexOf("formhash=") + 8,
				response.indexOf("formhash=") + 18));
		String formhash = response.substring(response.indexOf("formhash=") + 9,
				response.indexOf("formhash=") + 17);
//		System.out.println(response.indexOf("forum.php"));
		getMethod.releaseConnection();
		

		postMethod = new PostMethod();
		try {
			postMethod.setURI(new URI(baseUrl + "/misc.php?mod=swfupload&action=swfupload&operation=upload&fid=" + fid));
		} catch (URIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File tmpF = new File("view.jpg");
		if(tmpF.exists())
			tmpF.delete();
//		BufferedImage image = null;
//		try {
//			image = ImageIO.read(new URL(wm.getImgUrl()));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
//			ImageIO.write( image, "jpg", baos );
//			baos.flush();
//		byte[] imageInByte = baos.toByteArray();
//		baos.close();

		GetMethod method = new GetMethod(wm.getImgUrl());
		initHttpClient.getHttpClient().executeMethod(method);
		byte[] imageInByte = method.getResponseBody();
		FileUtils.writeByteArrayToFile(tmpF, imageInByte);
        FilePart fp = new FilePart("Filedata", tmpF);  
        Part[] parts = {fp,new StringPart("type", "image"),new StringPart("uid", "66815"),new StringPart("filetype", "jpg"),new StringPart("hash", "55febcdafc4626c834782281858f0377"),new StringPart("Upload", "Submit Query")};  
        MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());  
        postMethod.setRequestEntity(mre);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String imgf = null;
		try {
			imgf = new String(postMethod.getResponseBody(), "GBK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		postMethod=  new PostMethod();
		try {
			postMethod.setURI(new URI(baseUrl + "/forum.php?mod=post&action=newthread&fid=" + fid + "&topicsubmit=yes&infloat=yes&handlekey=fastnewpost&inajax=1&formhash=" + formhash));
		} catch (URIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postMethod.addParameter("message", wm.getDzContent());
		String s = wm.getTitle();
		if(wm.getStoreType().equals("亚马逊中国")){
			s = "【亚马逊中国】" + s;
		}else if(wm.getStoreType().equals("美国亚马逊")){
			s = "【amazon】" + s;
		}
		s = Util.getLimitLengthString(s, 80);
		postMethod.addParameter("subject", s);
		postMethod.addParameter("typeid", typeid);
		postMethod.addParameter("wysiwyg", "0");
		postMethod.addParameter("sortid", "5");
		postMethod.addParameter("selectsortid", "5");
		postMethod.addParameter("checkbox", "0");
		postMethod.addParameter("typeoption[awz]", "2");
		postMethod.addParameter("attachnew[" + imgf + "][description]", "");
		RequestEntity r;
		UrlEncodedFormEntity uef;
		postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=gbk");
		postMethod.setRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.49 Safari/537.36");
		postMethod.setRequestHeader("Origin", baseUrl);
		postMethod.setRequestHeader("Accept-Encoding", "deflate,sdch");
		postMethod
				.setRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		postMethod.setRequestHeader("Referer",baseUrl + "/forum.php?mod=viewthread&tid=107757&extra=page%3D2%26orderby%3Dlastpost");
		try {
			httpClient.executeMethod(postMethod);
			logger.info(postMethod.getResponseBodyAsString());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		ExecuteHtzj e = new ExecuteHtzj();
		e.init();
		e.username = "admin";
		e.password = "admin";
		e.fid = "2";
		e.post("http://localhost/php");
	}

	public void send(WeiboMsg wm) {
		// TODO Auto-generated method stub
		if(!wm.getStoreType().equals("亚马逊中国"))
			return;
		this.wm = wm;
			post("http://www.haitaozj.com");
	}
}
