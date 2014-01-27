package com.redrum.webapp.webController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redrum.webapp.weibo.BasicEntity;
import com.redrum.webapp.weibo.InitHttpClient;
import com.redrum.webapp.weibo.WeiboMsg;
import com.redrum.webapp.weibo.WeiboService;

@Controller
@RequestMapping("weibo")
public class WeiboController {
	@Autowired
	private WeiboService weiboService;
	
	@Autowired
	private InitHttpClient initHttpClient;

	@RequestMapping("query")
	@ResponseBody
	public List weiboQueryMsgs(){
		return weiboService.queryMsgs();
		
	}
	ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping("save")
	@ResponseBody
	public void save(String clazz, HttpServletRequest request){
		BasicEntity o = null;
		HashMap map = new HashMap();
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
		  String name = (String) names.nextElement();
		  map.put(name, request.getParameter(name));
		}
//		BeanUtils.populate(bean, map);
		try {
//			o = (BasicEntity) Class.forName(clazz).newInstance();
			o = (BasicEntity) mapper.convertValue(map, Class.forName(clazz));
//			BeanUtils.populate(o, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		weiboService.save(o);
	}
	
	
	@RequestMapping("delete")
	@ResponseBody
	public void delete(String clazz, HttpServletRequest request){
		BasicEntity o = null;
		HashMap map = new HashMap();
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
		  String name = (String) names.nextElement();
		  map.put(name, request.getParameter(name));
		}
		try {
			o = (BasicEntity) mapper.convertValue(map, Class.forName(clazz));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		weiboService.delete(o);
	}
	@RequestMapping("setCookie")
	@ResponseBody
	public String setCookie(String cookie){
		return cookie;
//		if(null != cookie)
//			initHttpClient.setCk(cookie);
//		return initHttpClient.getCk();
	}
	
	@RequestMapping("check")
	@ResponseBody
	public Boolean checkWeibo(){
	    return initHttpClient.checkWeibo();
	}
	
	@RequestMapping(value="ttt")
	@ResponseBody
	public void ttt(HttpServletRequest request){
//		request.getHeader("Content-Length")
		request.getHeader("Origin");
		request.getParameterMap();
		System.out.println(request);
	}
	@RequestMapping(value="weiboCallback")
	@ResponseBody
	public void weiboCallback(String state,String code){
//		request.getHeader("Content-Length")
		System.out.println(state);
		System.out.println(code);
	}
	@RequestMapping("queryTime")
	@ResponseBody
	public List queryTime(){
		List l = weiboService.getRanges();
		return l;
	}
	
	public static void main(String[] args) throws IOException {
		URL url = new URL("http://localhost:9081/webapp/weibo/ttt?cookie=abc");
		HttpURLConnection c = (HttpURLConnection) url.openConnection();
		c.setRequestMethod("POST");
		c.setRequestProperty("fdsa", "fdsa");
		c.setDoOutput(true);
		IOUtils.write("text=aaa&pic_id=&rank=0&rankid=&_surl=&location=&module=topquick&_t=0", c.getOutputStream(), "UTF-8");
		c.getOutputStream().close();
		System.out.println(c.getResponseCode());
	}


}
