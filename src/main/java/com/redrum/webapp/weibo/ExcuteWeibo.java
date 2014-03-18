package com.redrum.webapp.weibo;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.openide.util.ImageUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.redrum.webapp.weibo.entity.FansEntity;
import com.redrum.webapp.weibo.entity.TimeRangeEntity;
import com.redrum.webapp.weibo.entity.WeiboAccount;
import com.sun.jna.platform.win32.WTypes;

import weibo4j.Friendships;
import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

@Service
public class ExcuteWeibo {
	@Autowired
	private InitHttpClient initHttpClient;

	@Value("${access_token}")
	String access_token = "2.00Lj6eJEiAwHIBdb114dc228iRIPyD";
	
	@Autowired
	private Crawl3rdParty crawl3rdParty;

	private SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
	@Autowired
	private ExecuteHtzj executeHtzj;
	@Autowired
	private ExecuteGhaitao executeGhaitao;
	@Autowired
	private Execute55haitao execute55haitao;
	private Logger logger = Logger.getLogger(ExcuteWeibo.class);

	public Set getFans(Set result, String uid, int page) {
		logger.debug(uid + " page:" + page);
		initHttpClient.resetMethod();
		// initHttpClient.getHttpMethod().setPath(
		// "/p/" + uid + "/follow?relate=fans&page=" + page);
		initHttpClient.getHttpMethod().setPath("/p/" + uid + "/follow");
		initHttpClient.getHttpMethod().setQueryString(
				"relate=fans&page=" + page);
		String responseString = null;
		try {
			initHttpClient.getHttpClient().executeMethod(
					initHttpClient.getHttpMethod());
			responseString = initHttpClient.getHttpMethod()
					.getResponseBodyAsString();
			logger.debug(responseString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pattern pattern = Pattern.compile("action-data=\\\\\"uid=([^&]*)&");
		Matcher matcher = pattern.matcher(responseString);
//		logger.debug(responseString);
		// File f = new File("f_" + uid + "_" + page + ".log");
		// try {
		// f.createNewFile();
		// OutputStream os = new FileOutputStream(f);
		// IOUtils.write(responseString, os);
		// os.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		boolean end = true;
		while (matcher.find()) {
			String i = matcher.group(1);
			if (!result.contains(i)) {
				result.add(i);
				end = false;
			}
		}
		if (!end) {
			getFans(result, uid, page + 1);
		}
		return result;
	}

	public int follow(String uid) {
		for(WeiboAccount wa:weiboService.queryAccounts()){
			logger.info("follow by " + wa.getUsername());
			initHttpClient.account = wa;
		initHttpClient.resetMethod();
//		initHttpClient.getPostMethod().setPath("/aj/f/followed");
		try {
			initHttpClient.getPostMethod().setURI(new URI("http://www.weibo.com/aj/f/followed"));
		} catch (URIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		initHttpClient.getPostMethod().setQueryString(
				"_wv=5&__rnd=1382356548595");
		initHttpClient.getPostMethod().addParameter("uid", uid);
		initHttpClient.getPostMethod().addParameter("f", "1");
		// initHttpClient.getPostMethod().addParameter("extra", "");
		initHttpClient.getPostMethod().addParameter("refer_sort", "profile");
		initHttpClient.getPostMethod().addParameter("refer_flag",
				"profile_head");
		initHttpClient.getPostMethod().addParameter("location",
				"page_100505_home");
		// initHttpClient.getPostMethod().addParameter("oid", "2093492691");
		initHttpClient.getPostMethod().addParameter("wforce", "1");
		// initHttpClient.getPostMethod().addParameter("nogroup", "false");
		initHttpClient.getPostMethod().addParameter("_t", "0");
		try {
			initHttpClient.getHttpClient().executeMethod(
					initHttpClient.getPostMethod());
			logger.info(initHttpClient.getPostMethod().getStatusCode());
			logger.info(initHttpClient.getPostMethod()
					.getResponseBodyAsString());
			int res = initHttpClient.getPostMethod().getStatusCode();
			initHttpClient.getPostMethod().releaseConnection();
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.info(e.getMessage());
		}
		}
		initHttpClient.getPostMethod().releaseConnection();
		// PostMethod a;
		// a.set
		return 0;
	}

	@Scheduled(cron = "40 0/5 * * * *")
	private void batchFollowed(){

//		logger.info("start follow");
		List<FansEntity> fans = weiboService.getNextFansToFollowL();
//		logger.info("follow:" + fans.size());
		for (FansEntity fan : fans) {
			logger.info(fan.getScreenName());
			try {
				int res = follow(fan.getUserId());
				logger.info("res code:" + res);
				if(200== res){
				fan.setFollowing(true);
				logger.info("success followed " + fan.getScreenName());
				weiboService.save(fan);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void batchFollowed(String uid) {
		Set<String> fans = getFans(new HashSet(), uid, 1);
		logger.info("total find " + fans.size());
		for (String s : fans) {
			follow(s);
		}
	}

	public static int getLengthString(String str) {
		try {
			byte[] b = str.getBytes("gb2312");
			return b.length;
		} catch (Exception ex) {
			return 0;
		}
	}

	public static String getLimitLengthString(String str, int len) {
		try {
			int counterOfDoubleByte = 0;
			byte[] b = str.getBytes("gb2312");
			if (b.length <= len)
				return str;
			for (int i = 0; i < len; i++) {
				if (b[i] < 0) {
					counterOfDoubleByte++;
				}
			}
			if (counterOfDoubleByte % 2 == 0)
				return new String(b, 0, len, "gb2312");
			else
				return new String(b, 0, len - 1, "gb2312");
		} catch (Exception ex) {
			return "";
		}
	}

	public void sendWeibo(WeiboMsg wm) {
		// initHttpClient.resetMethod();
		// initHttpClient.getPostMethod().setPath("/aj/mblog/add");
		// initHttpClient.getPostMethod().setQueryString("_wv=5&__rnd=" +

		// initHttpClient.getPostMethod().addParameter("extra", "");
		// initHttpClient.getPostMethod().addParameter("oid", "2093492691");
		// initHttpClient.getPostMethod().addParameter("nogroup", "false");
		// initHttpClient.getPostMethod().addParameter("_t", "0");
		// initHttpClient.getPostMethod().addParameter("module", "topquick");
		// initHttpClient.getPostMethod().setRequestBody(new NameValuePair[]{
		// new NameValuePair("_t", "0"),
		// new NameValuePair("module", "topquick"),
		// new NameValuePair("location", ""),
		// new NameValuePair("_surl", ""),
		// new NameValuePair("rankid", "0"),
		// new NameValuePair("pic_id", ""),
		// new NameValuePair("text", "中文")
		// });
		// HttpURLConnection c =
		// initHttpClient.resetPostConnection("http://www.weibo.com/aj/mblog/add?_wv=5&__rnd="
		// HttpURLConnection c =
		// initHttpClient.resetGetConnection("http://weibo.com/aj/mblog/add");
		// c.setDoOutput(true);
		// try {
		// c.setRequestMethod("POST");
		// } catch (ProtocolException e2) {
		// // TODO Auto-generated catch block
		// e2.printStackTrace();
		// }
//		if(null == wm.getShotUrl()){
			wm.setShotUrl(crawl3rdParty.shortUrl(wm.getUrl()));
//		}
//			ImageUtilities.mergeImages(arg0, arg1, arg2, arg3)
		String s = wm.getTitle() + " " + wm.getContent();
		if(wm.getStoreType().equals("亚马逊中国")){
			s = "【亚马逊中国】" + s;
		}else if(wm.getStoreType().equals("美国亚马逊")){
			s = "【amazon】" + s;
		}
		int cl = 130;
		int ul = wm.getShotUrl().length();
		ul = ul - 140;
		if (ul > 0) {
			cl = cl - new Double(Math.ceil(0.5 * ul)).intValue();
		}
		// if(s.length() > cl){
		// s = s.substring(0, cl - 4) + "...";
		// }
		// System.out.println("cl=" + cl);
		// System.out.println("cl*2=" + cl*2);
		// System.out.println("s:" + s);
		s = getLimitLengthString(s, cl * 2);
		// System.out.println("s2:" + s);
//		System.out.println("s2+url:" + s + "\n" + wm.getShotUrl());
		logger.debug("s2+url:" + s + "\n" + wm.getShotUrl());
		// initHttpClient.getPostMethod().addParameter("text", new
		// String("中文".getBytes(), "UTF-8"));
		// initHttpClient.getPostMethod().addParameter("location", "");
		// initHttpClient.getPostMethod().addParameter("_surl", "");
		// initHttpClient.getPostMethod().addParameter("rankid", "0");
		// initHttpClient.getPostMethod().addParameter("pic_id", "");
		/*
		 * try { // initHttpClient.getPostMethod().setRequestEntity(new
		 * StringRequestEntity("text=" + URLEncoder.encode(s + "\n" +
		 * wm.getUrl()) +
		 * "&pic_id=&rank=0&rankid=&_surl=&location=&module=topquick&_t=0",
		 * PostMethod.FORM_URL_ENCODED_CONTENT_TYPE, "UTF-8"));
		 * IOUtils.write("text=" + URLEncoder.encode(s + "\n" + wm.getUrl()) +
		 * "&pic_id=&rank=0&rankid=&_surl=&location=&module=topquick&_t=0",
		 * c.getOutputStream(), "UTF-8"); // IOUtils.write("text=" +
		 * Math.random() +
		 * "&pic_id=&rank=0&rankid=&_surl=&location=&module=topquick&_t=0",
		 * c.getOutputStream(), "UTF-8"); // c.getOutputStream().flush();
		 * c.getOutputStream().close(); } catch (UnsupportedEncodingException
		 * e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		// String statuses = URLEncoder.encode(s + "\n" + wm.getUrl());
		String statuses = s + "\n" + wm.getUrl();
		try {
			ImageItem imageItem = null;
			if(null != wm.getImgUrl()){
//				URL url = new URL(wm.getImgUrl());
//			BufferedImage image = ImageIO.read();
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ImageIO.write( image, "jpg", baos );
//			baos.flush();
//			byte[] imageInByte = baos.toByteArray();
//			baos.close();
			GetMethod method = new GetMethod(wm.getImgUrl());
			initHttpClient.getHttpClient().executeMethod(method);
			byte[] imageInByte = method.getResponseBody();
			imageItem = new ImageItem(imageInByte);
			statuses = URLEncoder.encode(statuses);
			}else{
			}
			
			for(WeiboAccount wa:weiboService.queryAccounts()){
				try{
				Timeline tm = new Timeline();
				tm.client.setToken(wa.getAccessToken());
				if(null != wm.getImgUrl()){
					Status status = tm.UploadStatus(statuses, imageItem);
					Log.logInfo(status.toString());
				}else{
					Status status = tm.UpdateStatus(statuses);
					Log.logInfo(status.toString());
				}
				}catch(Exception e){e.printStackTrace();}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * try { //
		 * initHttpClient.getHttpClient().executeMethod(initHttpClient.getPostMethod
		 * ()); // c.connect();
		 * 
		 * //
		 * System.out.println(initHttpClient.getPostMethod().getStatusCode());
		 * //
		 * System.out.println(initHttpClient.getPostMethod().getResponseBodyAsString
		 * ()); System.out.println(IOUtils.toString(c.getInputStream()));
		 * System.out.println(c.getResponseCode()); // c.disconnect(); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	private Integer timeCount = 0;

	@Autowired
	private WeiboService weiboService;

	@Scheduled(cron = "0 0/5 * * * *")
//	@Scheduled(cron = "0 * * * * *")
	public void runSend() {
		timeCount += 5 * 60;
		List<WeiboMsg> list = weiboService.getCurrentSends();
		List<TimeRangeEntity> ranges = weiboService.getRanges();
//		System.out.println("list.size:" + list.size());
		logger.debug("list.size:" + list.size());
		out: for (WeiboMsg wm : list) {

//			if(wm.getSendHtzj()==null||wm.getSendHtzj()!=1){
//				wm.setSendHtzj(1);
//				executeHtzj.send(wm);
//				weiboService.save(wm);
//			}
			boolean send = false;
//			System.out.println(null==wm.getImmediately());
			logger.debug(null==wm.getImmediately());
			if (null==wm.getImmediately()||!wm.getImmediately()) {
//				System.out.println("ranges.size:" + ranges.size());
				logger.debug("ranges.size:" + ranges.size());
				for (TimeRangeEntity tre : ranges) {
//				    System.out.println(tre.getStores());
					logger.debug(tre.getStores());
					if (StringUtils.isNotBlank(tre.getStores())
							&& !tre.getStores().contains(wm.getStoreType()))
						continue;
//					System.out.println("pass the if");
					logger.debug("pass the if");
					Date startDate = Util.getChinaDate();
					Date endDate = Util.getChinaDate();
					try {
						Date tmp = sdf.parse(tre.getStartTime());
						startDate.setHours(tmp.getHours());
						startDate.setMinutes(tmp.getMinutes());
						tmp = sdf.parse(tre.getEndTime());
						endDate.setHours(tmp.getHours());
						endDate.setMinutes(tmp.getMinutes());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Date now = Util.getChinaDate();
//					System.out.println("startDate:" + startDate);
					logger.debug("startDate:" + startDate);
//					System.out.println("endDate:" + endDate);
					logger.debug("endDate:" + endDate);
//					System.out.println("now:" + now);
					logger.debug("now:" + now);
					if (!(now.after(startDate) && now.before(endDate))) {
						continue;
					}
					if (null == tre.getCount())
						tre.setCount(0);
					tre.setCount(tre.getCount() + 5);
					if (tre.getCount() < tre.getMaxCount()) {
						weiboService.save(tre);
						continue;
					}
					send = true;
					tre.setCount(0);
					weiboService.save(tre);
					break;
				}
			}
			if (send || wm.getImmediately()) {
				try {
					wm.setSendDate(new Date());
					sendWeibo(wm);
//					weiboService.save(wm);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(wm.getSendHtzj()==null||wm.getSendHtzj()!=1){
					wm.setSendHtzj(1);
					executeHtzj.send(wm);
//					weiboService.save(wm);
				}
				if(wm.getSendGht()==null||wm.getSendGht()!=1){
					wm.setSendGht(1);
//					executeGhaitao.send(wm);
				}
				if(wm.getSend55ht()==null||wm.getSend55ht()!=1){
					wm.setSend55ht(1);
					execute55haitao.send(wm);
				}
				weiboService.save(wm);
				break out;
			}
		}
		// if (timeCount >= 1 * 60 * 60) {
		// if (list.size() == 0) {
		// WeiboMsg wm = weiboService.getNextSend();
		// if (null != wm) {
		// try {
		// wm.setSendDate(new Date());
		// sendWeibo(wm);
		// weiboService.save(wm);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// timeCount = 0;
		// }

	}

	public static void main(String[] args) throws Exception {
		// ExcuteWeibo exe = new ExcuteWeibo();
		// exe.initHttpClient = new InitHttpClient();
		// exe.initHttpClient.afterPropertiesSet();
		// // exe.batchFollowed("1005051570845453");
		// // exe.follow("1195230310");
		// WeiboMsg wm = new WeiboMsg();
		// wm.setUrl("dfda");
		// wm.setContent("中文");
		// exe.sendWeibo(wm);
		// System.out.println(123);

//		try {
//			Date d = new SimpleDateFormat("hh:mm").parse("08:30");
//			System.out.println(d);
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		//http://pn.zdmimg.com/201312/18/328f9913.jpg_n1.jpg
		BufferedImage image = ImageIO.read(new URL("http://pn.zdmimg.com/201312/18/328f9913.jpg_n1.jpg"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( image, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
	}
}
