package com.redrum.webapp.webController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redrum.webapp.entity.TestEntity;
@Controller
@RequestMapping("/abc")
public class MainController {
	   @RequestMapping(value="/test")
	    public String test() {
		   System.out.println("hello world");
		   Map m = new HashMap();
		   m.put("abf", 123);
		   return "test";
	    }
	   
	   
	   @RequestMapping(value="/test2", produces="application/json")
	   @ResponseBody
	    public  TestEntity test2() {
		   System.out.println("hello world");
		   Map m = new HashMap();
		   m.put("abf", 123);
		   
		   TestEntity te = new TestEntity();
		   te.setStr("abc");
		   return te;
	    }

}
