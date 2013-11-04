package com.redrum.webapp.webController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


}
