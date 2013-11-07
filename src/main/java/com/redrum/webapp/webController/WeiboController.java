package com.redrum.webapp.webController;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redrum.webapp.weibo.BasicEntity;
import com.redrum.webapp.weibo.WeiboService;

@Controller
@RequestMapping("weibo")
public class WeiboController {
	@Autowired
	private WeiboService weiboService;

	@RequestMapping("query")
	@ResponseBody
	public List weiboQueryMsgs(){
		return weiboService.queryMsgs();
		
	}
	
	@RequestMapping("save")
	@ResponseBody
	public void save(HttpServletRequest request, String clazz){
		BasicEntity o = null;
		try {
			o = (BasicEntity) Class.forName(clazz).newInstance();
			BeanUtils.populate(o, request.getParameterMap());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		weiboService.save(o);
	}
	
	
	@RequestMapping("delete")
	@ResponseBody
	public void delete(Integer id){
		weiboService.delete(id);
	}


}
