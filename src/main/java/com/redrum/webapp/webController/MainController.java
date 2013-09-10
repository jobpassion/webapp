package com.redrum.webapp.webController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redrum.webapp.entity.TestEntity;
import com.redrum.webapp.service.TestService;

@Controller
public class MainController {
	@Autowired
	private TestService testService;

	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	@RequestMapping(value = "/test")
	public String test() {
		System.out.println("hello world");
		Map m = new HashMap();
		m.put("abf", 123);
		return "test";
	}

	@RequestMapping(value = "/test2", produces = "application/json")
	@ResponseBody
	public TestEntity test2() {
		System.out.println("hello world");
		Map m = new HashMap();
		m.put("abf", 123);

		TestEntity te = new TestEntity();
		te.setStr("abcd");
		return te;
	}

	@RequestMapping(value = "/test3")
	@ResponseBody
	public String test3() {
		System.out.println("hello world");
		Map m = new HashMap();
		m.put("abf", 123);
		return "mengfanhua";
	}

	@RequestMapping(value = "/test4")
	@ResponseBody
	public List test4() throws SQLException {
		List l = testService.queryAllTest();
		return l;
	}

}
