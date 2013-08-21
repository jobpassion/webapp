package com.redrum.webapp.chromeNote.web;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redrum.webapp.chromeNote.entity.Note;
import com.redrum.webapp.chromeNote.service.NoteService;
import com.redrum.webapp.entity.TestEntity;
import com.redrum.webapp.service.TestService;

@Controller
@RequestMapping(value = "/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/addNote")
	public void addNote(HttpServletRequest request){
		Note note = new Note();
		try {
			BeanUtils.populate(note, request.getParameterMap());
			noteService.addNote(note);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/saveNote")
	public void saveNote(HttpServletRequest request){
		Note note = new Note();
		try {
			BeanUtils.populate(note, request.getParameterMap());
			noteService.saveNote(note);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
